package com.bee32.sem.makebiz.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.Nullables;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.inventory.util.IMaterialConsumer;
import com.bee32.sem.makebiz.entity.MaterialPlan;

public class MaterialPlanDto
        extends StockJobDto<MaterialPlan>
        implements IMaterialConsumer {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PURCHASE_REQUEST = 2;

    MakeTaskDto task;
    MakeOrderDto order;

    List<MaterialPlanItemDto> items;

    @Override
    protected void _marshal(MaterialPlan source) {
        task = mref(MakeTaskDto.class, source.getTask());
        order = mref(MakeOrderDto.class, source.getOrder());

        if (selection.contains(ITEMS))
            items = marshalList(MaterialPlanItemDto.class, source.getItems());
        else
            items = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(MaterialPlan target) {
        merge(target, "task", task);
        merge(target, "order", order);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public MakeTaskDto getTask() {
        return task;
    }

    public void setTask(MakeTaskDto task) {
        if (task == null)
            throw new NullPointerException("task");
        this.task = task;
    }

    public MakeOrderDto getOrder() {
        return order;
    }

    public void setOrder(MakeOrderDto order) {
        this.order = order;
    }

    @SuppressWarnings("unchecked")
    public List<StockPlanOrderDto> getPlanOrders() {
        return (List<StockPlanOrderDto>) (Object) getStockOrders();
    }

    @SuppressWarnings("unchecked")
    public void setPlanOrders(List<StockPlanOrderDto> planOrders) {
        setStockOrders((List<StockOrderDto>) (Object) planOrders);
    }

    public List<MaterialPlanItemDto> getItems() {
        return items;
    }

    public void setItems(List<MaterialPlanItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(MaterialPlanItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(MaterialPlanItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        // item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    @Override
    public void declareConsumption(ConsumptionMap consumptionMap) {
        for (MaterialPlanItemDto item : items)
            item.declareConsumption(consumptionMap);
    }

    public void plan(StockOrderItemDto itemTemplate, BigDecimal quantity) {
        StockWarehouseDto warehouse = itemTemplate.getLocation().getWarehouse();

        StockPlanOrderDto planOrder = null;
        for (StockPlanOrderDto order : getPlanOrders()) {
            if (Nullables.equals(order.getWarehouse(), warehouse)) {
                planOrder = order;
                break;
            }
        }
        if (planOrder == null) {
            planOrder = new StockPlanOrderDto().create();
            planOrder.setJob(this);
            planOrder.setWarehouse(warehouse);
            getPlanOrders().add(planOrder);
        }

        StockOrderItemDto copy = new StockOrderItemDto().create();
        copy.populate(itemTemplate); // 导入
        copy.setId(-planOrder.getItems().size() - 1L, true);
        copy.setParent(planOrder);
        copy.setQuantity(quantity);
        planOrder.addItem(copy);
    }

}
