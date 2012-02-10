package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.StockPlanOrderDto;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.util.SelectItemHolder;

@ForEntity(MaterialPlan.class)
public class MaterialPlanAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MaterialPlanAdminBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
    }

    public void save1() {
        if (materialPlan.getId() == null) {
        }
        try {
            MakeTask task = _plan.getTask();
            if (!task.getPlans().contains(_plan)) {
                task.getPlans().add(_plan);
            }

            serviceFor(MaterialPlan.class).save(_plan);
            uiLogger.info("保存成功");
            // loadMaterialPlan(goNumber);
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息", e);
        }
    }

    public void chooseTask() {
        // 在此方法中，根据bom计算所需物料
        selectedTask = reload(selectedTask);

        if (selectedTask.getPlans().size() > 0) {
            uiLogger.error("此生产任务单已经有对应的物料计划!");
            return;
        }

        for (MakeTaskItemDto taskItem : selectedTask.getItems()) {
            PartDto part = reload(taskItem.getPart(), PartDto.CHILDREN);
            BigDecimal quantity = taskItem.getQuantity();

            Map<MaterialDto, BigDecimal> allMaterial = part.getAllMaterial();
            if (allMaterial != null) {
                for (MaterialDto m : allMaterial.keySet()) {
                    MaterialPlanItemDto item = new MaterialPlanItemDto().create();

                    item.setMaterialPlan(materialPlan);
                    item.setMaterial(m);
                    item.setQuantity(quantity.multiply(allMaterial.get(m))); // 产品数量乘以原物料数量

                    materialPlan.addItem(item);
                }
            }
        }

        materialPlan.setTask(selectedTask);
    }

    public void loadMaterialPlanOrder(int position) {
        if (materialPlan != null && materialPlan.getPlanOrders() != null && materialPlan.getPlanOrders().size() > 0) {

            planOrder = materialPlan.getPlanOrders().get(position - 1);
            orderItems = planOrder.getItems();

        } else {
            planOrder = new StockPlanOrderDto().create();
            orderItems = planOrder.getItems();
        }
    }

    public void queryStock() {
        List<MaterialPlanItemDto> items = getItems();
        if (items == null) {
            stockQueryItems = new ArrayList<SelectItemHolder>();
        }

        List<Long> materialIds = new ArrayList<Long>();
        for (MaterialPlanItemDto item : items)
            materialIds.add(item.getMaterial().getId());

        if (materialIds.size() <= 0) {
            stockQueryItems = new ArrayList<SelectItemHolder>();
        }

        StockQueryOptions opts = new StockQueryOptions(new Date(), true);
        opts.setCBatch(null, true);
        opts.setLocation(null, true);
        opts.setWarehouse(null, true);

        IStockQuery q = ctx.getBean(IStockQuery.class);
        StockOrder list = q.getActualSummary(materialIds, opts);
        List<StockOrderItemDto> queryResult = DTOs.marshalList(StockOrderItemDto.class, list.getItems());

        // 把查询结果变成SelectItemHoder的list,以便于绑定primefaces的DataTable组件
        if (queryResult != null & queryResult.size() > 0) {
            // 把items变成map,便于根据物料查询数量
            Map<MaterialDto, BigDecimal> materialQueryMap = new HashMap<MaterialDto, BigDecimal>();
            for (MaterialPlanItemDto __planItem : items) {
                materialQueryMap.put(__planItem.getMaterial(), __planItem.getQuantity());
            }

            // 为一个物料同时在不同仓库中有数量的情况使用
            Map<MaterialDto, BigDecimal> alreadySetQuantityMap = new HashMap<MaterialDto, BigDecimal>();

            stockQueryItems = new ArrayList<SelectItemHolder>();
            for (StockOrderItemDto queryItem : queryResult) {
                SelectItemHolder holder = new SelectItemHolder();
                holder.setItem(queryItem);

                BigDecimal needQuantity = materialQueryMap.get(queryItem.getMaterial());
                BigDecimal lastQuantity = alreadySetQuantityMap.get(queryItem.getMaterial());
                if (lastQuantity != null) {
                    // 说明出现了同一个物料在不同仓库中同时存在的情
                    needQuantity = needQuantity.subtract(lastQuantity);
                }

                if (needQuantity.compareTo(queryItem.getQuantity()) <= 0) {
                    holder.setQuantity(needQuantity);
                } else {
                    holder.setQuantity(queryItem.getQuantity());
                }

                alreadySetQuantityMap.put(queryItem.getMaterial(), holder.getQuantity());
                holder.setChecked(true);

                stockQueryItems.add(holder);
            }
        } else {
            stockQueryItems = new ArrayList<SelectItemHolder>();
        }
    }

    public void lockStock() {
        List<StockPlanOrderDto> orders = new ArrayList<StockPlanOrderDto>();
        for (SelectItemHolder holder : stockQueryItems) {
            if (holder.isChecked()) {
                StockPlanOrderDto order = null;
                for (StockPlanOrderDto o : orders) {
                    if (o.getWarehouse().getId() == holder.getItem().getLocation().getWarehouse().getId()) {
                        order = o;
                        break;
                    }
                }

                if (order == null) {
                    order = new StockPlanOrderDto().create();
                    order.setSubject(StockOrderSubject.PLAN_OUT);
                    order.setPlan(materialPlan);
                    order.setWarehouse(holder.getItem().getLocation().getWarehouse());
                    orders.add(order);
                }

                StockOrderItemDto item = new StockOrderItemDto().create();
                item.setParent(order);
                item.setBatch(holder.getItem().getBatch());
                item.setExpirationDate(holder.getItem().getExpirationDate());
                item.setLocation(holder.getItem().getLocation());
                item.setMaterial(holder.getItem().getMaterial());
                item.setPrice(holder.getItem().getPrice());
                item.setQuantity(holder.getQuantity());

                order.addItem(item);
            }
        }

        if (orders.size() > 0) {
            materialPlan.setPlanOrders(orders);
        }
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
