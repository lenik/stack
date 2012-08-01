package com.bee32.sem.purchase.web;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.validation.RequiredId;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.web.business.StockDictsBean;
import com.bee32.sem.makebiz.dto.MaterialPlanDto;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.dto.PurchaseTakeInDto;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;
import com.bee32.sem.purchase.service.PurchaseService;
import com.bee32.sem.purchase.util.PurchaseCriteria;

@ForEntity(PurchaseRequest.class)
public class PurchaseRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    static final int TAB_BASIC = 0;
    static final int TAB_PLANS = 1;
    static final int TAB_ITEMS = 2;
    static final int TAB_TAKEINS = 3;

    int tabIndex;

    public PurchaseRequestAdminBean() {
        super(PurchaseRequest.class, PurchaseRequestDto.class, 0);
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * 用户每选择一次plan,都调用本方法。 由request.addPlan(plan)来记录多次选择的结果
     */
    public void setMaterialPlanToAttach(MaterialPlanDto plan) {
        if (plan == null)
            return;

        List<PurchaseRequest> boundRequests = ctx.data.access(PurchaseRequest.class).list(
                PurchaseCriteria.boundRequest(plan.getId()));
        if (!boundRequests.isEmpty()) {
            uiLogger.error("选中的物料计划已经有对应的采购请求");
            return;
        }

        PurchaseRequestDto request = getOpenedObject();
        plan = reload(plan, MaterialPlanDto.ITEMS);

        //把物料计划的简要附加到采购请求的描述中
        StringBuilder descBuilder = new StringBuilder();
        if(request.getDescription().length() > 0) {
            descBuilder.append(request.getDescription());
        }
        descBuilder.append(plan.getLabel());
        descBuilder.append(";");
        request.setDescription(descBuilder.toString());

        request.addPlan(plan);
    }

    /**
     * 由物料计划计算采购量
     */
    public void calcMaterialRequirement() {
        PurchaseRequestDto parent = getOpenedObject();

        PurchaseService purchaseService = ctx.bean.getBean(PurchaseService.class);

        List<PurchaseRequestItemDto> items = purchaseService.calcMaterialRequirement(parent.getPlans());

        // 为返回的PurchaseRequestItem设置parent
        for (PurchaseRequestItemDto item : items)
            item.setParent(parent);

        parent.setItems(items);
    }

    @RequiredId(zeroForNull = true)
    public int getDestWarehouseId_RZ() {
        PurchaseRequestItemDto item = itemsMBean.getOpenedObject();
        Integer id = item.getDestWarehouse().getId();
        return id == null ? 0 : id;
    }

    public void setDestWarehouseId_RZ(int warehouseId) {
        StockDictsBean stockDicts = ctx.bean.getBean(StockDictsBean.class);
        PurchaseRequestItemDto item = itemsMBean.getOpenedObject();
        item.setDestWarehouse(stockDicts.getWarehouse(warehouseId));
    }

    public void showTakeIns() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelection(Fmask.F_MORE);
    }

    /**
     * 生成产购入库单
     */
    public void generateTakeInStockOrders() {
        PurchaseRequestDto purchaseRequest = getOpenedObject();

        if (!purchaseRequest.getTakeIns().isEmpty()) {
            uiLogger.error("采购请求已经生成过采购入库单.");
            return;
        }
        for (PurchaseRequestItemDto item : purchaseRequest.getItems())
            if (DTOs.isNull(item.getDestWarehouse())) {
                uiLogger.error("所有采购请求的明细都必须选择对应的入库${inventoryTerms.warehouse}!");
                return;
            }
        for (PurchaseRequestItemDto requestItem : purchaseRequest.getItems())
            if (DTOs.isNull(requestItem.getAcceptedInquiry())) {
                uiLogger.error("采购请求项目没有对应的采购建议.");
                return;
            }


        VerifyEvalState state = purchaseRequest.getVerifyContext().getVerifyEvalState();
        if(!VerifyEvalState.VERIFIED.equals(state)) {
            uiLogger.error("采购请求还没有审核.");
            return;
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

    /**
     * 保存已经生成的采购入库
     */
    @Transactional
    public void saveTakeInStockOrders() {
        PurchaseRequestDto purchaseRequest = getOpenedObject();

        List<PurchaseTakeInDto> takeIns = purchaseRequest.getTakeIns();

        try {
            for(PurchaseTakeInDto takeIn : takeIns) {
                PurchaseTakeIn _takeIn = takeIn.unmarshal();
                ctx.data.access(PurchaseTakeIn.class).saveOrUpdate(_takeIn);
            }

            uiLogger.info("采购入库单保存成功.");
        } catch(Exception e) {
            uiLogger.error("采购入库单保存失败!", e);
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    @Override
    protected Integer getFmaskOverride(int saveFlags) {
        return super.getFmaskOverride(saveFlags);
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<MaterialPlanDto> plansMBean = ListMBean.fromEL(this, //
            "openedObject.plans", MaterialPlanDto.class);
    final ListMBean<PurchaseRequestItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", PurchaseRequestItemDto.class);
    final ListMBean<PurchaseTakeInDto> takeInsMBean = ListMBean.fromEL(this, //
            "openedObject.takeIns", PurchaseTakeInDto.class);
    final ListMBean<StockOrderItemDto> orderItemsMBean = ListMBean.fromEL(takeInsMBean, //
            "openedObject.stockOrder.items", StockOrderItemDto.class);

    public ListMBean<MaterialPlanDto> getPlansMBean() {
        return plansMBean;
    }

    public ListMBean<PurchaseRequestItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public ListMBean<PurchaseTakeInDto> getTakeInsMBean() {
        return takeInsMBean;
    }

    public ListMBean<StockOrderItemDto> getOrderItemsMBean() {
        return orderItemsMBean;
    }

}
