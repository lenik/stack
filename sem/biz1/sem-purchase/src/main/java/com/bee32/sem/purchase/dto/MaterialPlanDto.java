package com.bee32.sem.purchase.dto;

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
import com.bee32.sem.purchase.entity.MaterialPlan;

public class MaterialPlanDto
        extends StockJobDto<MaterialPlan>
        implements IMaterialConsumer {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PURCHASE_REQUEST = 2;

    MakeTaskDto task;
    List<MaterialPlanItemDto> items;
    PurchaseRequestDto purchaseRequest;

    @Override
    protected void _marshal(MaterialPlan source) {
        task = mref(MakeTaskDto.class, source.getTask());

        if (selection.contains(ITEMS))
            items = marshalList(MaterialPlanItemDto.class, source.getItems());
        else
            items = Collections.emptyList();

        if (selection.contains(PURCHASE_REQUEST))
            purchaseRequest = mref(PurchaseRequestDto.class, source.getPurchaseRequest());
        else
            // XXX: Optim: is purchase request entity fetched here??
            purchaseRequest = new PurchaseRequestDto().ref(source.getPurchaseRequest());
    }

    @Override
    protected void _unmarshalTo(MaterialPlan target) {
        merge(target, "task", task);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        if (selection.contains(PURCHASE_REQUEST))
            merge(target, "purchaseRequest", purchaseRequest);
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

    public PurchaseRequestDto getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequestDto purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
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
