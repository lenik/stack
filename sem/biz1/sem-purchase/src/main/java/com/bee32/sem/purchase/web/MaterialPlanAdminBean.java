package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.PlanOrderDto;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.util.SelectItemHolder;

@ForEntity(MaterialPlan.class)
public class MaterialPlanAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    private MaterialPlanDto materialPlan = new MaterialPlanDto().create();
    private MaterialPlanItemDto materialPlanItem = new MaterialPlanItemDto().create();

    private MaterialDto selectedMaterial;

    private boolean newItemStatus = false;

    protected List<MaterialPlanItemDto> itemsNeedToRemoveWhenModify = new ArrayList<MaterialPlanItemDto>();

    private MakeTaskDto selectedTask;

    private StockOrderSubject subject = null;
    private StockOrderItemDto orderItem = new StockOrderItemDto().create();

    private PartyDto selectedSupplier;

    private List<SelectItemHolder> stockQueryItems;

    private PlanOrderDto planOrder;
    private int goNumberPlanOrder;
    private int countPlanOrder;

    private List<StockOrderItemDto> orderItems;

    public MaterialPlanAdminBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
        goNumberPlanOrder = 1;
        loadMaterialPlanOrder(goNumberPlanOrder);

        subject = StockOrderSubject.PLAN_OUT;
    }

    public MaterialPlanDto getMaterialPlan() {
        return materialPlan;
    }

    public void setMaterialPlan(MaterialPlanDto materialPlan) {
        this.materialPlan = materialPlan;
    }

    public String getCreator() {
        if (materialPlan == null)
            return "";
        else
            return materialPlan.getOwnerDisplayName();
    }

    public List<MaterialPlanItemDto> getItems() {
        if (materialPlan == null)
            return null;
        return materialPlan.getItems();
    }

    public MaterialPlanItemDto getMaterialPlanItem() {
        return materialPlanItem;
    }

    public void setMaterialPlanItem(MaterialPlanItemDto materialPlanItem) {
        this.materialPlanItem = materialPlanItem;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    public MakeTaskDto getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(MakeTaskDto selectedTask) {
        this.selectedTask = selectedTask;
    }

    public List<MakeTaskItemDto> getTaskItems() {
        if (selectedTask != null) {
            return selectedTask.getItems();
        }
        return null;
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        this.subject = subject;
    }

    public StockOrderItemDto getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(StockOrderItemDto orderItem) {
        this.orderItem = orderItem;
    }

    public PartyDto getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(PartyDto selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

    public PlanOrderDto getPlanOrder() {
        return planOrder;
    }

    public void setPlanOrder(PlanOrderDto planOrder) {
        this.planOrder = planOrder;
    }

    public int getGoNumberPlanOrder() {
        return goNumberPlanOrder;
    }

    public void setGoNumberPlanOrder(int goNumberPlanOrder) {
        this.goNumberPlanOrder = goNumberPlanOrder;
    }

    public int getCountPlanOrder() {
        if (materialPlan != null && materialPlan.getPlanOrders() != null)
            countPlanOrder = materialPlan.getPlanOrders().size();
        else
            countPlanOrder = 0;
        return countPlanOrder;
    }

    public void setCountPlanOrder(int countPlanOrder) {
        this.countPlanOrder = countPlanOrder;
    }

    public List<StockOrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<StockOrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public List<SelectItemHolder> getStockQueryItems() {
        return stockQueryItems;
    }

    public void setStockQueryItems(List<SelectItemHolder> stockQueryItems) {
        this.stockQueryItems = stockQueryItems;
    }

    private void loadMaterialPlan(int position) {
        // 刷新总记录数

        materialPlan = new MaterialPlanDto().create(); // 如果限定条件内没有找到materialPlan,则创建一个

        MaterialPlan firstPlan = serviceFor(MaterialPlan.class).getFirst( //
                new Offset(position - 1), //
// CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstPlan != null)
            materialPlan = DTOs.marshal(MaterialPlanDto.class, MaterialPlanDto.ITEMS | MaterialPlanDto.ORDERS,
                    firstPlan);

    }

    public void modify() {
        if (materialPlan.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();
    }

    public void save1() {
        if (materialPlan.getId() == null) {
            // 新增
// goNumber = count + 1;
        }

        try {
            MaterialPlan _plan = materialPlan.unmarshal();
            for (MaterialPlanItemDto item : itemsNeedToRemoveWhenModify) {
                _plan.removeItem(item.unmarshal());
            }

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

    public void newItem() {
        materialPlanItem = new MaterialPlanItemDto().create();
        materialPlanItem.setMaterialPlan(materialPlan);

        newItemStatus = true;
    }

    public void modifyItem() {
        selectedSupplier = null;
        newItemStatus = false;
    }

    public void saveItem() {
        materialPlanItem.setMaterialPlan(materialPlan);
        materialPlanItem.setPreferredSupplier(selectedSupplier);
        if (newItemStatus) {
            materialPlan.addItem(materialPlanItem);
        }
    }

    public void delete() {
        try {
            serviceFor(MaterialPlan.class).delete(materialPlan.unmarshal());
            uiLogger.info("删除成功!");
// loadMaterialPlan(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    public void deleteItem() {
        materialPlan.removeItem(materialPlanItem);

        if (materialPlanItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(materialPlanItem);
        }
    }

    public void chooseMaterial() {
        materialPlanItem.setMaterial(selectedMaterial);

        selectedMaterial = null;
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

    public void findSupplier() {
// PeopleCriteria.suppliers(), //
    }

    public void firstPlanOrder() {
        goNumberPlanOrder = 1;
        loadMaterialPlanOrder(goNumberPlanOrder);
    }

    public void previousPlanOrder() {
        goNumberPlanOrder--;
        if (goNumberPlanOrder < 1)
            goNumberPlanOrder = 1;
        loadMaterialPlanOrder(goNumberPlanOrder);
    }

    public void goPlanOrder() {
        if (goNumberPlanOrder < 1) {
            goNumberPlanOrder = 1;
        } else if (goNumberPlanOrder > countPlanOrder) {
            goNumberPlanOrder = countPlanOrder;
        }
        loadMaterialPlanOrder(goNumberPlanOrder);
    }

    public void nextPlanOrder() {
        goNumberPlanOrder++;

        if (goNumberPlanOrder > countPlanOrder)
            goNumberPlanOrder = countPlanOrder;
        loadMaterialPlanOrder(goNumberPlanOrder);
    }

    public void lastPlanOrder() {
        goNumberPlanOrder = countPlanOrder + 1;
        loadMaterialPlanOrder(goNumberPlanOrder);
    }

    public void loadMaterialPlanOrder(int position) {
        // 刷新总记录数
        getCountPlanOrder();

        goNumberPlanOrder = position;

        if (position < 1) {
            goNumberPlanOrder = 1;
            position = 1;
        }
        if (goNumberPlanOrder > countPlanOrder) {
            goNumberPlanOrder = countPlanOrder;
            position = countPlanOrder;
        }

        if (materialPlan != null && materialPlan.getPlanOrders() != null && materialPlan.getPlanOrders().size() > 0) {

            planOrder = materialPlan.getPlanOrders().get(position - 1);
            orderItems = planOrder.getItems();

        } else {
            planOrder = new PlanOrderDto().create();
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

        IStockQuery q = getBean(IStockQuery.class);
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
        List<PlanOrderDto> orders = new ArrayList<PlanOrderDto>();
        for (SelectItemHolder holder : stockQueryItems) {
            if (holder.isChecked()) {
                PlanOrderDto order = null;
                for (PlanOrderDto o : orders) {
                    if (o.getWarehouse().getId() == holder.getItem().getLocation().getWarehouse().getId()) {
                        order = o;
                        break;
                    }
                }

                if (order == null) {
                    order = new PlanOrderDto().create();
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
            goNumberPlanOrder = 1;
            loadMaterialPlanOrder(goNumberPlanOrder);
        }
    }
}
