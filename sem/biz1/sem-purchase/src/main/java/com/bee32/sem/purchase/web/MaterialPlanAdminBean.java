package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.StockPlanOrderDto;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.util.LockItemConfig;

@ForEntity(MaterialPlan.class)
public class MaterialPlanAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<LockItemConfig> lockItemConfigs = new ArrayList<LockItemConfig>();

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

            Map<MaterialDto, BigDecimal> allMaterial = part.getMaterialConsumption();
            long index = 0;
            for (Entry<MaterialDto, BigDecimal> ent : allMaterial.entrySet()) {
                MaterialPlanItemDto planItem = new MaterialPlanItemDto().create();
                planItem.setMaterialPlan(materialPlan);
                planItem.setMaterial(ent.getKey());
                planItem.setQuantity(quantity.multiply(ent.getValue())); // 产品数量乘以原物料数量
                planItem.setId(index++, true);
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

        List<Long> materialIds = new ArrayList<Long>();
        for (MaterialPlanItemDto item : materialPlan.getItems())
            materialIds.add(item.getMaterial().getId());

        StockQueryOptions opts = new StockQueryOptions(new Date(), true);
        opts.setCBatch(null, true);
        opts.setLocation(null, true);
        opts.setWarehouse(null, true);

        IStockQuery q = ctx.bean.getBean(IStockQuery.class);
        StockOrder _sumOrder = q.getActualSummary(materialIds, opts);
        StockOrderDto sumOrder = DTOs.marshal(StockOrderDto.class, StockOrderDto.ITEMS, _sumOrder);

        // 把查询结果变成SelectItemHoder的list,以便于绑定primefaces的DataTable组件
        // 把items变成map,便于根据物料查询数量
        Map<MaterialDto, BigDecimal> needMap = new HashMap<MaterialDto, BigDecimal>();
        for (MaterialPlanItemDto planItem : materialPlan.getItems()) {
            needMap.put(planItem.getMaterial(), planItem.getQuantity());
        }

        lockItemConfigs.clear();
        for (StockOrderItemDto sumItem : sumOrder.getItems()) {
            MaterialDto material = sumItem.getMaterial();
            BigDecimal needQuantity = needMap.get(material);
            if (needQuantity.compareTo(BigDecimal.ZERO) <= 0)
                continue;

            LockItemConfig itemConfig = new LockItemConfig();
            itemConfig.setItem(sumItem);
            itemConfig.setChecked(true);

            BigDecimal configQuantity = needQuantity.min(sumItem.getQuantity());
            itemConfig.setQuantity(configQuantity);

            needQuantity = needQuantity.subtract(configQuantity);
            needMap.put(material, needQuantity);

            lockItemConfigs.add(itemConfig);
        }

        for (Entry<MaterialDto, BigDecimal> entry : needMap.entrySet()) {
            MaterialDto material = entry.getKey();
            BigDecimal needQuantity = entry.getValue();
            if (needQuantity.compareTo(BigDecimal.ZERO) > 0) {
                uiLogger.warn("库存中的 " + material.getLabel() + " 数量不足：还需要 " + needQuantity + " "
                        + material.getUnit().getLabel());
            }
        }
        uiLogger.info("库存导入完成");
    }

    /**
     * 根据用户的勾选，形成库存锁定（每个仓库对应一个锁定单）。
     */
    public void lockStock() {
        MaterialPlanDto materialPlan = getOpenedObject();
        Map<StockWarehouseDto, StockPlanOrderDto> planOrders = new HashMap<>();

        for (LockItemConfig itemConfig : lockItemConfigs) {
            if (!itemConfig.isChecked())
                continue;

            StockOrderItemDto fromItem = itemConfig.getItem();
            StockWarehouseDto warehouse = fromItem.getLocation().getWarehouse();
            StockPlanOrderDto planOrder = planOrders.get(warehouse);
            if (planOrder == null) {
                planOrder = new StockPlanOrderDto().create();
                planOrder.setJob(materialPlan);
                planOrder.setWarehouse(warehouse);
                planOrders.put(warehouse, planOrder);
            }

            StockOrderItemDto toItem = new StockOrderItemDto().create();
            toItem.populate(itemConfig.getItem()); // 导入
            toItem.setParent(planOrder);
            toItem.setQuantity(itemConfig.getQuantity());
            planOrder.addItem(toItem);
        }

        if (!planOrders.isEmpty()) {
            List<StockPlanOrderDto> planOrderList = new ArrayList<StockPlanOrderDto>();
            planOrderList.addAll(planOrders.values());
            materialPlan.setPlanOrders(planOrderList);
        }
    }

    public List<LockItemConfig> getLockItemConfigs() {
        return lockItemConfigs;
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
