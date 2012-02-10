package com.bee32.sem.purchase.web;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.PurchaseAdviceDto;
import com.bee32.sem.purchase.dto.PurchaseInquiryDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.entity.PurchaseAdvice;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;
import com.bee32.sem.purchase.service.PurchaseService;

@ForEntity(PurchaseRequest.class)
public class PurchaseRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    PurchaseAdviceDto purchaseAdvice;

    public PurchaseRequestAdminBean() {
        super(PurchaseRequest.class, PurchaseRequestDto.class, 0);
    }

    public PurchaseAdviceDto getPurchaseAdvice() {
        if (purchaseAdvice == null || purchaseAdvice.getId() == null)
            purchaseAdvice = new PurchaseAdviceDto().create();
        if (purchaseAdvice.getPreferredInquiry() == null || purchaseAdvice.getPreferredInquiry().getId() == null) {
            PurchaseInquiryDto tmpInquiry = new PurchaseInquiryDto().create();
            purchaseAdvice.setPreferredInquiry(tmpInquiry);
        }
        return purchaseAdvice;
    }

    public void setPurchaseAdvice(PurchaseAdviceDto purchaseAdvice) {
        this.purchaseAdvice = purchaseAdvice;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (PurchaseRequest request : uMap.<PurchaseRequest> entitySet()) {
            List<MaterialPlan> plans = request.getPlans();
            for (MaterialPlan plan : plans)
                plan.setPurchaseRequest(request);
            serviceFor(MaterialPlan.class).saveOrUpdateAll(plans);
        }
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (PurchaseRequest request : uMap.<PurchaseRequest> entitySet()) {
            List<MaterialPlan> plans = request.getPlans();
            for (MaterialPlan plan : plans)
                plan.setPurchaseRequest(null);
            asFor(MaterialPlan.class).saveOrUpdateAll(plans);
        }
        return true;
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

        PurchaseService purchaseService = ctx.getBean(PurchaseService.class);
        List<PurchaseRequestItemDto> items = purchaseService.calcMaterialRequirement(purchaseRequest, selectedPlans);
        purchaseRequest.setItems(items);
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

    ListMBean<PurchaseRequestItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items",
            PurchaseRequestItemDto.class);
    ListMBean<PurchaseInquiryDto> inquiresMBean = ListMBean.fromEL(itemsMBean, "openedObject.inquires",
            PurchaseInquiryDto.class);

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

    public void generateTakeInStockOrders() {
        PurchaseRequestDto purchaseRequest = getOpenedObject();
        for (PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            if (item.getWarehouseId() == null) {
                uiLogger.error("所有采购请求的明细都必须选择对应的入库仓库!");
                return;
            }
        }

        PurchaseService purchaseService = ctx.getBean(PurchaseService.class);
        try {
            purchaseService.generateTakeInStockOrders(purchaseRequest);
            uiLogger.info("生成成功");
        } catch (Exception e) {
            uiLogger.error("错误", e);
            return;
        }
    }

    public List<?> getSelectedPurchaseAdvices() {
        return listOfNonNulls(purchaseAdvice);
    }

}
