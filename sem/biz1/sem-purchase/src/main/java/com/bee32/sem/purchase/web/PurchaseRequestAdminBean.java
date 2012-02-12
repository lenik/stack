package com.bee32.sem.purchase.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.PurchaseInquiryDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.dto.PurchaseTakeInDto;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.service.PurchaseService;

@ForEntity(PurchaseRequest.class)
public class PurchaseRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PurchaseRequestAdminBean() {
        super(PurchaseRequest.class, PurchaseRequestDto.class, 0);
    }

    /** 更新物料：设置关联 */
    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (PurchaseRequest request : uMap.<PurchaseRequest> entitySet()) {
            List<MaterialPlan> plans = request.getPlans();
            for (MaterialPlan plan : plans)
                plan.setPurchaseRequest(request);
            ctx.data.access(MaterialPlan.class).saveOrUpdateAll(plans);
        }
    }

    /** 更新物料计划：取消关联 */
    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (PurchaseRequest request : uMap.<PurchaseRequest> entitySet()) {
            List<MaterialPlan> plans = request.getPlans();
            for (MaterialPlan plan : plans)
                plan.setPurchaseRequest(null);
            ctx.data.access(MaterialPlan.class).saveOrUpdateAll(plans);
        }
        return true;
    }

    MaterialPlanDto selectedMaterialPlan;

    public void addMaterialPlan() {
        if (selectedMaterialPlan == null)
            return;
        if (selectedMaterialPlan.getPurchaseRequest().getId() != null) {
            uiLogger.error("选中的物料计划已经有对应的采购请求，请重新选择");
            return;
        }

        PurchaseRequestDto purchaseRequest = getOpenedObject();
        purchaseRequest.getPlans().add(selectedMaterialPlan);

        PurchaseService purchaseService = ctx.bean.getBean(PurchaseService.class);
        List<PurchaseRequestItemDto> items = purchaseService.calcMaterialRequirement(purchaseRequest,
                Arrays.asList(selectedMaterialPlan));
        purchaseRequest.setItems(items);

    }

    public void loadInquiry() {
        PurchaseRequestItemDto itemObj = itemsMBean.getOpenedObject();
        itemObj = reload(itemObj);
    }

    @Transactional
    public void acceptInquiry() {
        uiLogger.info("采纳成功.");
// uiLogger.error("采纳失败.", e);
    }

    public void savePurchaseAdvice() {
        // if (purchaseAdvice == null || purchaseAdvice.getId() == null) {
        uiLogger.warn("请先采纳一个供应商的保价");
    }

    public void deletePurchaseAdvice() {
        // TODO 如果已经审核，则不能删除
        // if (purchaseAdvice.getVerifyContext().getVerifyState().isFinalized())
        // uiLogger.warn("采购建议已经审核，不能删除!");
        // return;
        // }
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

        PurchaseService purchaseService = ctx.bean.getBean(PurchaseService.class);
        try {
            purchaseService.generateTakeInStockOrders(purchaseRequest);
            uiLogger.info("生成成功");
        } catch (Exception e) {
            uiLogger.error("错误", e);
            return;
        }
    }

    final ListMBean<MaterialPlanDto> plansMBean = ListMBean.fromEL(this, //
            "openedObject.plans", MaterialPlanDto.class);
    final ListMBean<PurchaseRequestItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", PurchaseRequestItemDto.class);
    final ListMBean<PurchaseInquiryDto> inquiresMBean = ListMBean.fromEL(itemsMBean, //
            "openedObject.inquires", PurchaseInquiryDto.class);
    final ListMBean<PurchaseTakeInDto> takeInsMBean = ListMBean.fromEL(this, //
            "openedObject.takeIns", PurchaseTakeInDto.class);

    public ListMBean<MaterialPlanDto> getPlansMBean() {
        return plansMBean;
    }

    public ListMBean<PurchaseRequestItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public ListMBean<PurchaseInquiryDto> getInquiresMBean() {
        return inquiresMBean;
    }

    public ListMBean<PurchaseTakeInDto> getTakeInsMBean() {
        return takeInsMBean;
    }

}
