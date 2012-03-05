package com.bee32.sem.purchase.web;

import java.util.List;

import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.Identities;
import com.bee32.plover.orm.util.RefsDiff;
import com.bee32.plover.orm.validation.RequiredId;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.web.business.StockDictsBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
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

    @Override
    protected Integer getFmaskOverride(int saveFlags) {
        return Fmask.F_MORE & ~PurchaseRequestDto.PLANS;
    }

    /** 更新物料：设置关联 */
    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        UnmarshalMap dPlans = uMap.deltaMap("plans");
        dPlans.setLabel("关联的物料计划");
        dPlans.setEntityClass(MaterialPlan.class);

        for (PurchaseRequest _purchaseRequest : uMap.<PurchaseRequest> entitySet()) {
            PurchaseRequestDto purchaseRequest = uMap.getSourceDto(_purchaseRequest);
            RefsDiff diff = Identities.compare(_purchaseRequest.getPlans(), purchaseRequest.getPlans());
            for (MaterialPlan _detached : diff.<MaterialPlan> leftOnly()) {
                _detached.setPurchaseRequest(null);
                dPlans.put(_detached, null);
            }
            for (MaterialPlanDto attached : diff.<MaterialPlanDto> rightOnly()) {
                MaterialPlan _attached = attached.unmarshal();
                _attached.setPurchaseRequest(_purchaseRequest);
                dPlans.put(_attached, attached);
            }
        }
        return true;
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

    /**
     * 用户每选择一次plan,都调用本方法。
     * 由parent.addPlan(plan)来记录多次选择的结果
     */
    public void setMaterialPlanToAttach(MaterialPlanDto plan) {
        if (plan == null)
            return;
        if (plan.getPurchaseRequest().getId() != null) {
            uiLogger.error("选中的物料计划已经有对应的采购请求");
            return;
        }

        PurchaseRequestDto parent = getOpenedObject();
        plan = reload(plan, MaterialPlanDto.ITEMS);
        parent.addPlan(plan);
    }

    /**
     * 由物料计划计算采购量
     */
    public void calcMaterialRequirement() {
        PurchaseRequestDto parent = getOpenedObject();

        PurchaseService purchaseService = ctx.bean.getBean(PurchaseService.class);

        List<PurchaseRequestItemDto> items = purchaseService.calcMaterialRequirement(parent.getPlans());

        //为返回的PurchaseRequestItem设置parent
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

    /**
     * 生成产购入库单
     */
    public void generateTakeInStockOrders() {
        PurchaseRequestDto purchaseRequest = getOpenedObject();
        for (PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            if (DTOs.isNull(item.getDestWarehouse())) {
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
    final ListMBean<PurchaseTakeInDto> takeInsMBean = ListMBean.fromEL(this, //
            "openedObject.takeIns", PurchaseTakeInDto.class);

    public ListMBean<MaterialPlanDto> getPlansMBean() {
        return plansMBean;
    }

    public ListMBean<PurchaseRequestItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public ListMBean<PurchaseTakeInDto> getTakeInsMBean() {
        return takeInsMBean;
    }

}
