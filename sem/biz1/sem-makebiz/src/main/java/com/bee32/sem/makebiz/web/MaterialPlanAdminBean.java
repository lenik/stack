package com.bee32.sem.makebiz.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.dto.MaterialPlanDto;
import com.bee32.sem.makebiz.dto.MaterialPlanItemDto;
import com.bee32.sem.makebiz.dto.StockPlanOrderDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.makebiz.entity.MaterialPlan;
import com.bee32.sem.makebiz.service.MakebizService;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(MaterialPlan.class)
public class MaterialPlanAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MaterialPlanAdminBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        // Update hibernate cache
        for (MaterialPlan _plan : uMap.<MaterialPlan> entitySet()) {
            if (_plan.getOrder() == null && _plan.getTask() == null) {
                uiLogger.error("物料计划缺少对应的证单或生产任务!");
                return false;
            }


            MakeTask _task = _plan.getTask();
            if ((_task != null) && (!_task.getPlans().contains(_plan)))
                _task.getPlans().add(_plan);

            MakeOrder _order = _plan.getOrder();
            if ((_order != null) && (!_order.getPlans().contains(_plan)))
                _order.getPlans().add(_plan);
        }
        return true;
    }

    /**
     * 根据bom计算所需物料
     */
    public void setMakeTaskToApply(MakeTaskDto task) {
        if (task == null) {
            uiLogger.error("没有选中生产任务。");
            return;
        }
        MaterialPlanDto materialPlan = getOpenedObject();

        MakebizService service = ctx.bean.getBean(MakebizService.class);
        service.calcMaterialPlanFromBom(materialPlan, task);

        uiLogger.info("计算完成。");
    }

    /**
     * 外购产品,则定单直接生成
     */
    public void setMakeOrderToApply(MakeOrderDto order) {
        MaterialPlanDto materialPlan = getOpenedObject();
        MakeOrderDto makeOrder = reload(order, MakeOrderDto.NOT_ARRANGED_ITEMS);
        List<MaterialPlanItemDto> planItems = makeOrder.arrangeMaterialPlan();
        if (planItems.isEmpty()) {
            uiLogger.error("此订单上的产品已经全部安排为生产任务或外购物料计划!");
            return;
        }
        materialPlan.setOrder(order);
        materialPlan.setItems(planItems);
        if (StringUtils.isEmpty(materialPlan.getLabel()))
            materialPlan.setLabel(makeOrder.getLabel());
    }


    /**
     * 查询可用库存，并形成一个锁定列表
     */
    public void importFromStock() {
        MaterialPlanDto materialPlan = getOpenedObject();
        materialPlan.getPlanOrders().clear();

        List<Long> materialIds = new ArrayList<Long>();
        for (MaterialPlanItemDto item : materialPlan.getItems())
            materialIds.add(item.getMaterial().getId());

        StockQueryOptions opts = new StockQueryOptions(new Date(), true);
        opts.setBatchArray(null, true);
        opts.setLocation(null, true);
        opts.setWarehouse(null, true);
        StockQueryResult queryResult = ctx.bean.getBean(IStockQuery.class).getAvailableStock(materialIds, opts);
        StockOrderDto sumOrder = DTOs.marshal(StockOrderDto.class, StockOrderDto.ITEMS, queryResult);

        // 把items变成map,便于根据物料查询数量
        ConsumptionMap cmap = new ConsumptionMap();
        materialPlan.declareConsumption(cmap);

        for (StockOrderItemDto sumItem : sumOrder.getItems()) {
            MaterialDto material = sumItem.getMaterial();
            BigDecimal needQuantity = cmap.getConsumption(material);
            if (needQuantity.compareTo(BigDecimal.ZERO) <= 0)
                continue;

            BigDecimal plannedQuantity = needQuantity.min(sumItem.getQuantity());
            // needQuantity = needQuantity.subtract(plannedQuantity);
            cmap.supply(material, plannedQuantity);

            materialPlan.plan(sumItem/* 作为拷贝模版 */, plannedQuantity);
        }

        for (Entry<MaterialDto, BigDecimal> entry : cmap.dtoMap().entrySet()) {
            MaterialDto material = entry.getKey();
            BigDecimal needQuantity = entry.getValue();
            if (needQuantity.compareTo(BigDecimal.ZERO) > 0) {
                uiLogger.warn("库存中的 " + material.getLabel() + " 数量不足：需要采购 " + needQuantity + " "
                        + material.getUnit().getLabel());
            }
        }
        uiLogger.info("库存导入完成。");
    }

    ListMBean<MaterialPlanItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", MaterialPlanItemDto.class);
    ListMBean<StockPlanOrderDto> planOrdersMBean = ListMBean.fromEL(this, "openedObject.planOrders",
            StockPlanOrderDto.class);
    ListMBean<StockOrderItemDto> planOrderItemsMBean = ListMBean.fromEL(planOrdersMBean, "openedObject.items",
            StockOrderItemDto.class);

    public ListMBean<MaterialPlanItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public ListMBean<StockPlanOrderDto> getPlanOrdersMBean() {
        return planOrdersMBean;
    }

    public ListMBean<StockOrderItemDto> getPlanOrderItemsMBean() {
        return planOrderItemsMBean;
    }

}
