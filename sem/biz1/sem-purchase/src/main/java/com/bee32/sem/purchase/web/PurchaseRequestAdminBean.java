package com.bee32.sem.purchase.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.purchase.dto.InquiryDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.PurchaseAdviceDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.Inquiry;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.service.PurchaseService;
import com.bee32.sem.world.monetary.CurrencyUtil;

public class PurchaseRequestAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    protected PurchaseRequestDto purchaseRequest = new PurchaseRequestDto().create();

    protected PurchaseRequestItemDto purchaseRequestItem = new PurchaseRequestItemDto().create();

    private boolean newItemStatus = false;

    protected List<PurchaseRequestItemDto> itemsNeedToRemoveWhenModify = new ArrayList<PurchaseRequestItemDto>();

    private Date limitDateFromForPlan;
    private Date limitDateToForPlan;

    private List<MaterialPlanDto> plans;
    private MaterialPlanDto selectedPlan;

    private Long selectedPlanId;
    private List<MaterialPlanDto> selectedPlans = new ArrayList<MaterialPlanDto>();

    private String supplierPattern;
    private List<PartyDto> suppliers;
    private PartyDto selectedSupplier;


    int inquiryDetailStatus;    //1-新增;2-修改;3-查看
    InquiryDto selectedInquiry;
    PurchaseAdviceDto purchaseAdvice;

    public PurchaseRequestAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();
        limitDateFromForPlan = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();
        limitDateToForPlan = c.getTime();

        goNumber = 1;
        loadPurchaseRequest(goNumber);
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public Date getLimitDateFrom() {
        return limitDateFrom;
    }

    public void setLimitDateFrom(Date limitDateFrom) {
        this.limitDateFrom = limitDateFrom;
    }

    public Date getLimitDateTo() {
        return limitDateTo;
    }

    public void setLimitDateTo(Date limitDateTo) {
        this.limitDateTo = limitDateTo;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }


    public int getCount() {
        count = serviceFor(PurchaseRequest.class).count(//
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
        return count;
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

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    public Date getLimitDateFromForPlan() {
        return limitDateFromForPlan;
    }

    public void setLimitDateFromForPlan(Date limitDateFromForPlan) {
        this.limitDateFromForPlan = limitDateFromForPlan;
    }

    public Date getLimitDateToForPlan() {
        return limitDateToForPlan;
    }

    public void setLimitDateToForPlan(Date limitDateToForPlan) {
        this.limitDateToForPlan = limitDateToForPlan;
    }

    public List<MaterialPlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlanDto> plans) {
        this.plans = plans;
    }

    public MaterialPlanDto getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(MaterialPlanDto selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public List<MaterialPlanItemDto> getPlanItems() {
        if(selectedPlan != null) {
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

    public List<MaterialPlanDto> getSelectedPlans() {
        return selectedPlans;
    }

    public void setSelectedPlans(List<MaterialPlanDto> selectedPlans) {
        this.selectedPlans = selectedPlans;
    }

    public String getSupplierPattern() {
        return supplierPattern;
    }

    public void setSupplierPattern(String supplierPattern) {
        this.supplierPattern = supplierPattern;
    }

    public List<PartyDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<PartyDto> suppliers) {
        this.suppliers = suppliers;
    }

    public PartyDto getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(PartyDto selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public int getInquiryDetailStatus() {
        return inquiryDetailStatus;
    }

    public void setInquiryDetailStatus(int inquiryDetailStatus) {
        this.inquiryDetailStatus = inquiryDetailStatus;
    }

    public List<InquiryDto> getInquiries() {
        if (purchaseRequestItem != null)
            return purchaseRequestItem.getInquiries();
        return null;
    }

    public InquiryDto getSelectedInquiry() {
        if(selectedInquiry == null)
            selectedInquiry = new InquiryDto().create();
        return selectedInquiry;
    }

    public void setSelectedInquiry(InquiryDto selectedInquiry) {
        this.selectedInquiry = selectedInquiry;
    }

    public PurchaseAdviceDto getPurchaseAdvice() {
        if (purchaseAdvice == null)
            purchaseAdvice = new PurchaseAdviceDto().create();
        if (purchaseAdvice.getPreferredInquiry() == null) {
            InquiryDto tmpInquiry = new InquiryDto().create();
            purchaseAdvice.setPreferredInquiry(tmpInquiry);
        }
        return purchaseAdvice;
    }

    public void setPurchaseAdvice(PurchaseAdviceDto purchaseAdvice) {
        this.purchaseAdvice = purchaseAdvice;
    }






    public void limit() {
        loadPurchaseRequest(goNumber);
    }

    private void loadPurchaseRequest(int position) {
        //刷新总记录数
        getCount();

        goNumber = position;

        if(position < 1) {
            goNumber = 1;
            position = 1;
        }
        if(goNumber > count) {
            goNumber = count;
            position = count;
        }

        purchaseRequest = new PurchaseRequestDto().create();    //如果限定条件内没有找到purchaseRequest,则创建一个

        PurchaseRequest firstRequest = serviceFor(PurchaseRequest.class).getFirst( //
                new Offset(position - 1), //
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstRequest != null) {
            purchaseRequest = DTOs.marshal(PurchaseRequestDto.class, PurchaseRequestDto.ITEMS | PurchaseRequestDto.PLANS, firstRequest);
            selectedPlans = purchaseRequest.getPlans();
        }

    }

    public void new_() {
        purchaseRequest = new PurchaseRequestDto().create();
        selectedPlans = new ArrayList<MaterialPlanDto>();
        editable = true;
    }

    public void modify() {
        if(purchaseRequest.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    @Transactional
    public void save() {
        if(purchaseRequest.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            PurchaseRequest _request = purchaseRequest.unmarshal();
            for(PurchaseRequestItemDto item : itemsNeedToRemoveWhenModify) {
                _request.removeItem(item.unmarshal());
            }

            serviceFor(PurchaseRequest.class).save(_request);

            for(MaterialPlanDto _p : selectedPlans) {
                MaterialPlan __p = _p.unmarshal();
                __p.setPurchaseRequest(_request);
                serviceFor(MaterialPlan.class).saveOrUpdate(__p);
            }

            uiLogger.info("保存成功");
            loadPurchaseRequest(goNumber);
            editable = false;

        } catch (Exception e) {
            uiLogger.warn("保存失败", e);
        }
    }

    @Transactional
    public void delete() {
        try {
            PurchaseRequest _purchaseRequest = purchaseRequest.unmarshal();
            _purchaseRequest.getPlans().clear();

            for(MaterialPlanDto _p : selectedPlans) {
                MaterialPlan __p = _p.unmarshal();
                __p.setPurchaseRequest(null);
                serviceFor(MaterialPlan.class).saveOrUpdate(__p);
            }

            serviceFor(PurchaseRequest.class).delete(_purchaseRequest);

            uiLogger.info("删除成功!");
            loadPurchaseRequest(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败", e);
        }
    }

    public void cancel() {
        loadPurchaseRequest(goNumber);
        editable = false;
    }

    public void first() {
        goNumber = 1;
        loadPurchaseRequest(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadPurchaseRequest(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadPurchaseRequest(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadPurchaseRequest(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadPurchaseRequest(goNumber);
    }

    public void newItem() {
        purchaseRequestItem = new PurchaseRequestItemDto().create();
        purchaseRequestItem.setPurchaseRequest(purchaseRequest);

        newItemStatus = true;
    }

    public void modifyItem() {
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

    public void findPlan() {
        List<MaterialPlan> _plans = serviceFor(MaterialPlan.class).list( //
                EntityCriteria.createdBetweenEx(limitDateFromForPlan, limitDateToForPlan));

        plans = DTOs.marshalList(MaterialPlanDto.class, _plans);
    }

    public void addPlan() {
        boolean alreadyAdded = false;
        for(MaterialPlanDto p : selectedPlans) {
            if(p.getId().equals(selectedPlan.getId())) {
                alreadyAdded = true;
                break;
            }
        }
        if(!alreadyAdded) {
            selectedPlans.add(selectedPlan);
        }
    }

    public void deletePlan() {
        for(MaterialPlanDto p : selectedPlans) {
            if(p.getId().equals(selectedPlanId)) {
                selectedPlans.remove(p);
                break;
            }
        }
    }

    public void choosePlan() {
        for(MaterialPlanDto _p : selectedPlans) {
            _p = reload(_p);
            if(_p.getPurchaseRequest().getId() != null) {
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
        if (supplierPattern != null && !supplierPattern.isEmpty()) {

            List<Party> _suppliers = serviceFor(Party.class).list( //
                    PeopleCriteria.suppliers(), //
                    new Or( //
                            new Like("name", "%" + supplierPattern + "%"), //
                            new Like("fullName", "%" + supplierPattern + "%")));

            suppliers = DTOs.marshalList(PartyDto.class, _suppliers);
        }
    }





    public void chooseSupplier() {
        selectedInquiry.setParty(selectedSupplier);
    }

    public void loadInquiry() {
        purchaseAdvice = purchaseRequestItem.getPurchaseAdvice();
        if (purchaseAdvice == null)
            purchaseAdvice = new PurchaseAdviceDto().create();
    }

    public void newInquiry() {
        inquiryDetailStatus = 1;
        selectedInquiry = new InquiryDto().create();
    }

    public void acceptInquiry() {

    }

    public void savePurchaseAdvice() {

    }

    public void saveInquiry() {
        if (inquiryDetailStatus == 1) {
            purchaseRequestItem.addInquiry(selectedInquiry);
            selectedInquiry.setPurchaseRequestItem(purchaseRequestItem);
        }

        serviceFor(Inquiry.class).saveOrUpdate(selectedInquiry.unmarshal());
    }

    public void deleteInquiry() {

    }
}
