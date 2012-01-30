package com.bee32.sem.purchase.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.purchase.dto.InquiryDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.PurchaseAdviceDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.Inquiry;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.entity.PurchaseAdvice;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;
import com.bee32.sem.purchase.service.PurchaseService;
import com.bee32.sem.world.monetary.CurrencyUtil;

@ForEntity(PurchaseRequest.class)
public class PurchaseRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    PurchaseRequestDto purchaseRequest = new PurchaseRequestDto().create();
    PurchaseRequestItemDto purchaseRequestItem = new PurchaseRequestItemDto().create();

    boolean newItemStatus = false;
    List<PurchaseRequestItemDto> itemsNeedToRemoveWhenModify = new ArrayList<PurchaseRequestItemDto>();

    MaterialPlanDto selectedPlan;
    Long selectedPlanId;
    OrgDto selectedSupplier;

    Integer inquiryDetailStatus; // 1-新增;2-修改;3-查看
    InquiryDto selectedInquiry;
    PurchaseAdviceDto purchaseAdvice;

    public final int INQUIRY_DETAIL_STATUS_NEW = 1;
    public final int INQUIRY_DETAIL_STATUS_MODIFY = 2;
    public final int INQUIRY_DETAIL_STATUS_VIEW = 3;

    public PurchaseRequestAdminBean() {
        super(PurchaseRequest.class, PurchaseRequestDto.class, 0);
    }

    public int getINQUIRY_DETAIL_STATUS_NEW() {
        return INQUIRY_DETAIL_STATUS_NEW;
    }

    public int getINQUIRY_DETAIL_STATUS_MODIFY() {
        return INQUIRY_DETAIL_STATUS_MODIFY;
    }

    public int getINQUIRY_DETAIL_STATUS_VIEW() {
        return INQUIRY_DETAIL_STATUS_VIEW;
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public PurchaseRequestDto getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequestDto purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public String getCreator() {
        if (purchaseRequest == null)
            return "";
        else
            return purchaseRequest.getOwnerDisplayName();
    }

    public List<PurchaseRequestItemDto> getItems() {
        if (purchaseRequest == null)
            return null;
        return purchaseRequest.getItems();
    }

    public PurchaseRequestItemDto getPurchaseRequestItem() {
        return purchaseRequestItem;
    }

    public void setPurchaseRequestItem(PurchaseRequestItemDto purchaseRequestItem) {
        this.purchaseRequestItem = purchaseRequestItem;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    public MaterialPlanDto getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(MaterialPlanDto selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public List<MaterialPlanItemDto> getPlanItems() {
        if (selectedPlan != null) {
            return selectedPlan.getItems();
        }
        return null;
    }

    public Long getSelectedPlanId() {
        return selectedPlanId;
    }

    public void setSelectedPlanId(Long selectedPlanId) {
        this.selectedPlanId = selectedPlanId;
    }

    public OrgDto getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(OrgDto selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public Integer getInquiryDetailStatus() {
        return inquiryDetailStatus;
    }

    public void setInquiryDetailStatus(Integer inquiryDetailStatus) {
        this.inquiryDetailStatus = inquiryDetailStatus;
    }

    public List<InquiryDto> getInquiries() {
        if (purchaseRequestItem != null)
            return purchaseRequestItem.getInquiries();
        return null;
    }

    public InquiryDto getSelectedInquiry() {
        if (selectedInquiry == null)
            selectedInquiry = new InquiryDto().create();
        return selectedInquiry;
    }

    public void setSelectedInquiry(InquiryDto selectedInquiry) {
        this.selectedInquiry = selectedInquiry;
    }

    public PurchaseAdviceDto getPurchaseAdvice() {
        if (purchaseAdvice == null || purchaseAdvice.getId() == null)
            purchaseAdvice = new PurchaseAdviceDto().create();
        if (purchaseAdvice.getPreferredInquiry() == null || purchaseAdvice.getPreferredInquiry().getId() == null) {
            InquiryDto tmpInquiry = new InquiryDto().create();
            purchaseAdvice.setPreferredInquiry(tmpInquiry);
        }
        return purchaseAdvice;
    }

    public void setPurchaseAdvice(PurchaseAdviceDto purchaseAdvice) {
        this.purchaseAdvice = purchaseAdvice;
    }

    private void loadPurchaseRequest(int position) {
        // 刷新总记录数

        purchaseRequest = new PurchaseRequestDto().create(); // 如果限定条件内没有找到purchaseRequest,则创建一个

        PurchaseRequest firstRequest = serviceFor(PurchaseRequest.class).getFirst( //
                new Offset(position - 1), //
//                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstRequest != null) {
            purchaseRequest = DTOs.marshal(PurchaseRequestDto.class, PurchaseRequestDto.ITEMS
                    | PurchaseRequestDto.PLANS, firstRequest);
            selectedPlans = purchaseRequest.getPlans();
        }

    }

    public void modify() {
        if (purchaseRequest.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();
    }

    @Transactional
    public void save1() {
        if (purchaseRequest.getId() == null) {
            // 新增
//            goNumber = count + 1;
        }

        try {
            PurchaseRequest _request = purchaseRequest.unmarshal();
            for (PurchaseRequestItemDto item : itemsNeedToRemoveWhenModify) {
                _request.removeItem(item.unmarshal());
            }

            serviceFor(PurchaseRequest.class).save(_request);

            for (MaterialPlanDto _p : selectedPlans) {
                MaterialPlan __p = _p.unmarshal();
                __p.setPurchaseRequest(_request);
                serviceFor(MaterialPlan.class).saveOrUpdate(__p);
            }

            uiLogger.info("保存成功");
//            loadPurchaseRequest(goNumber);

        } catch (Exception e) {
            uiLogger.warn("保存失败", e);
        }
    }

    @Transactional
    public void delete() {
        try {
            PurchaseRequest _purchaseRequest = purchaseRequest.unmarshal();
            _purchaseRequest.getPlans().clear();

            for (MaterialPlanDto _p : selectedPlans) {
                MaterialPlan __p = _p.unmarshal();
                __p.setPurchaseRequest(null);
                serviceFor(MaterialPlan.class).saveOrUpdate(__p);
            }

            serviceFor(PurchaseRequest.class).delete(_purchaseRequest);

            uiLogger.info("删除成功!");
//            loadPurchaseRequest(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败", e);
        }
    }

    public void newItem() {
        purchaseRequestItem = new PurchaseRequestItemDto().create();
        purchaseRequestItem.setPurchaseRequest(purchaseRequest);

        newItemStatus = true;
    }

    public void modifyItem() {
        selectedSupplier = (OrgDto) purchaseRequestItem.getPreferredSupplier();
        newItemStatus = false;
    }

    public void saveItem() {
        purchaseRequestItem.setPurchaseRequest(purchaseRequest);
        purchaseRequestItem.setPreferredSupplier(selectedSupplier);
        if (newItemStatus) {
            purchaseRequest.addItem(purchaseRequestItem);
        }
    }

    public void deleteItem() {
        purchaseRequest.removeItem(purchaseRequestItem);

        if (purchaseRequestItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(purchaseRequestItem);
        }
    }

    public void choosePlan() {
        for (MaterialPlanDto _p : selectedPlans) {
            _p = reload(_p);
            if (_p.getPurchaseRequest().getId() != null) {
                uiLogger.info("选中的物料计划已经有对应的采购请求,请重新选择");
                return;
            }
        }

        purchaseRequest.setPlans(selectedPlans);

        List<PurchaseRequestItemDto> items //
        = getBean(PurchaseService.class).calcMaterialRequirement(purchaseRequest, selectedPlans);
        purchaseRequest.setItems(items);
    }

    public void findSupplier() {
// PeopleCriteria.suppliers(), //
    }

    public void chooseSupplier() {
        selectedInquiry.setOrg(selectedSupplier);
    }

    public void loadInquiry() {
        purchaseRequestItem = reload(purchaseRequestItem);
        purchaseAdvice = purchaseRequestItem.getPurchaseAdvice();
        if (purchaseAdvice == null || purchaseAdvice.getId() == null) {
            purchaseAdvice = new PurchaseAdviceDto().create();
        } else {
            purchaseAdvice = reload(purchaseAdvice);
        }
    }

    public void newInquiry() {
        inquiryDetailStatus = INQUIRY_DETAIL_STATUS_NEW;
        selectedInquiry = new InquiryDto().create();
    }

    public void saveInquiry() {
        try {
            if (!purchaseRequest.getVerifyContext().getVerifyEvalState().equals(VerifyEvalState.VERIFIED)) {
                uiLogger.error("采购请求还没有审核!");
                return;
            }

            selectedInquiry.setPurchaseRequestItem(purchaseRequestItem);
            Inquiry _inquiry = selectedInquiry.unmarshal();
            serviceFor(Inquiry.class).saveOrUpdate(_inquiry);
            if (inquiryDetailStatus == INQUIRY_DETAIL_STATUS_NEW) {
                purchaseRequestItem.addInquiry(DTOs.marshal(InquiryDto.class, 0, _inquiry));
            }
            uiLogger.info("保存成功.");
        } catch (Exception e) {
            uiLogger.error("保存失败.", e);
        }
    }

    public void deleteInquiry() {
        try {
            serviceFor(Inquiry.class).deleteById(selectedInquiry.getId());
            purchaseRequestItem = reload(purchaseRequestItem);
            uiLogger.info("删除成功.");
        } catch (Exception e) {
            uiLogger.error("删除失败.", e);
        }
    }

    @Transactional
    public void acceptInquiry() {
        try {
            PurchaseAdvice _purchaseAdvice = purchaseAdvice.unmarshal();
            PurchaseRequestItem _purchaseRequestItem = purchaseRequestItem.unmarshal();

            _purchaseAdvice.setPreferredInquiry(selectedInquiry.unmarshal());
            _purchaseAdvice.setPurchaseRequestItem(_purchaseRequestItem);
            serviceFor(PurchaseAdvice.class).saveOrUpdate(_purchaseAdvice);
            purchaseAdvice = DTOs.marshal(PurchaseAdviceDto.class, _purchaseAdvice);

            _purchaseRequestItem.setPurchaseAdvice(_purchaseAdvice);
            serviceFor(PurchaseRequestItem.class).saveOrUpdate(_purchaseRequestItem);

            uiLogger.info("采纳成功.");
        } catch (Exception e) {
            uiLogger.error("采纳失败.", e);
        }
    }

    public void savePurchaseAdvice() {
        if (purchaseAdvice == null || purchaseAdvice.getId() == null) {
            uiLogger.warn("请先采纳一个供应商的保价");
            return;
        }

        try {
            serviceFor(PurchaseAdvice.class).saveOrUpdate(purchaseAdvice.unmarshal());
            uiLogger.info("保存成功.");
        } catch (Exception e) {
            uiLogger.error("保存失败.", e);
        }
    }

    public void deletePurchaseAdvice() {
        if (purchaseAdvice == null || purchaseAdvice.getId() == null) {
            return;
        }

        // TODO 如果已经审核，则不能删除
        // if (purchaseAdvice.getVerifyContext().getVerifyState().isFinalized())
// uiLogger.warn("采购建议已经审核，不能删除!");
// return;
// }

        try {
            PurchaseAdvice _purchaseAdvice = purchaseAdvice.unmarshal();
            _purchaseAdvice.getPurchaseRequestItem().setPurchaseAdvice(null);
            serviceFor(PurchaseAdvice.class).delete(_purchaseAdvice);
            purchaseAdvice = new PurchaseAdviceDto().create();
            uiLogger.info("删除成功.");
        } catch (Exception e) {
            uiLogger.error("删除失败.", e);
        }
    }

    public void verifyPurchaseAdvice() {
        // TODO add verify code
    }

    public void genTakeInStockOrder() {
        for (PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            if (item.getWarehouseId() == null) {
                uiLogger.error("所有采购请求的明细都必须选择对应的入库仓库!");
                return;
            }
        }

        try {
            getBean(PurchaseService.class).genTakeInStockOrder(purchaseRequest);
            uiLogger.info("生成成功");
        } catch (Exception e) {
            uiLogger.error("错误", e);
            return;
        }
    }

    @Override
    public List<?> getSelection() {
        return listOfNonNulls(purchaseRequest);
    }

    public List<?> getSelectedPurchaseAdvices() {
        return listOfNonNulls(purchaseAdvice);
    }
}
