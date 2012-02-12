package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.StockPlanOrderDto;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sem.purchase.entity.MaterialPlan;

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
            MakeTask _task = _plan.getTask();
            if (!_task.getPlans().contains(_plan))
                _task.getPlans().add(_plan);
        }
        return true;
    }

    /**
     * 根据bom计算所需物料
     */
    public void setApplyMakeTask(MakeTaskDto makeTaskRef) {
        if (makeTaskRef == null) {
            uiLogger.error("没有选中生产任务。");
            return;
        }
        MaterialPlanDto materialPlan = getOpenedObject();
        MakeTaskDto makeTask = reload(makeTaskRef, MakeTaskDto.ITEMS | MakeTaskDto.PLANS);

        if (!makeTask.getPlans().isEmpty()) {
            uiLogger.error("此生产任务单已经有对应的物料计划!");
            return;
        }

        materialPlan.setTask(makeTaskRef);
        if (StringUtils.isEmpty(materialPlan.getLabel()))
            materialPlan.setLabel(makeTask.getLabel());
        materialPlan.getItems().clear();

        for (MakeTaskItemDto taskItem : makeTask.getItems()) {
            PartDto part = reload(taskItem.getPart(), PartDto.MATERIAL_CONSUMPTION);
            BigDecimal quantity = taskItem.getQuantity();

            Map<MaterialDto, BigDecimal> allMaterial = part.getMaterialConsumption().dtoMap();
            long index = 0;
            for (Entry<MaterialDto, BigDecimal> ent : allMaterial.entrySet()) {
                MaterialPlanItemDto planItem = new MaterialPlanItemDto().create();
                planItem.setId(-(index++) - 1L, true);
                planItem.setMaterialPlan(materialPlan);
                planItem.setMaterial(ent.getKey());
                planItem.setQuantity(quantity.multiply(ent.getValue())); // 产品数量乘以原物料数量
                materialPlan.addItem(planItem);
            }
        }
        // 清空物料锁定。
        materialPlan.getPlanOrders().clear();
    }

    /**
     * 查询可用库存，并形成一个锁定列表让用户勾选。
     */
    public void importFromStock() {
        MaterialPlanDto materialPlan = getOpenedObject();
        materialPlan.getPlanOrders().clear();

        List<Long> materialIds = new ArrayList<Long>();
        for (MaterialPlanItemDto item : materialPlan.getItems())
            materialIds.add(item.getMaterial().getId());

        StockQueryOptions opts = new StockQueryOptions(new Date(), true);
        opts.setCBatch(null, true);
        opts.setLocation(null, true);
        opts.setWarehouse(null, true);
        StockQueryResult queryResult = ctx.bean.getBean(IStockQuery.class).getAvailableStock(materialIds, opts);
        StockOrderDto sumOrder = DTOs.marshal(StockOrderDto.class, StockOrderDto.ITEMS, queryResult);

        // 把查询结果变成SelectItemHoder的list,以便于绑定primefaces的DataTable组件
        // 把items变成map,便于根据物料查询数量
        ConsumptionMap cmap = new ConsumptionMap();
        materialPlan.declareConsumption(cmap);

        for (StockOrderItemDto sumItem : sumOrder.getItems()) {
            MaterialDto material = sumItem.getMaterial();
            BigDecimal needQuantity = cmap.getConsumption(material);
            if (needQuantity.compareTo(BigDecimal.ZERO) <= 0)
                continue;

            BigDecimal plannedQuantity = needQuantity.min(sumItem.getQuantity());
            needQuantity = needQuantity.subtract(plannedQuantity);
            cmap.consume(material, needQuantity);

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
