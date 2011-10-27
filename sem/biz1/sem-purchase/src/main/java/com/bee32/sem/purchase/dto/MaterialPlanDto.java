package com.bee32.sem.purchase.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.purchase.entity.MaterialPlan;

public class MaterialPlanDto
        extends StockJobDto<MaterialPlan> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PURCHASE_REQUEST = 2;

    MakeTaskDto task;
    StockOrderDto planOrder;

    String memo;

    List<MaterialPlanItemDto> items;

    PurchaseRequestDto purchaseRequest;

    @Override
    protected void _marshal(MaterialPlan source) {
        task = mref(MakeTaskDto.class, source.getTask());

        if (selection.contains(ORDERS)) {
            int orderSelection = selection.translate(ITEMS, StockOrderDto.ITEMS);
            planOrder = mref(StockOrderDto.class, orderSelection, source.getPlanOrder());
        }

        memo = source.getMemo();

        if (selection.contains(ITEMS))
            items = marshalList(MaterialPlanItemDto.class, source.getItems());
        else
            items = new ArrayList<MaterialPlanItemDto>();

        if (selection.contains(PURCHASE_REQUEST))
            purchaseRequest = mref(PurchaseRequestDto.class, source.getPurchaseRequest());
    }

    @Override
    protected void _unmarshalTo(MaterialPlan target) {
        merge(target, "task", task);

        if (selection.contains(ORDERS))
            merge(target, "planOrder", planOrder);

        target.setMemo(memo);

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

    public StockOrderDto getPlanOrder() {
        return planOrder;
    }

    public void setPlanOrder(StockOrderDto planOrder) {
        if (planOrder == null)
            throw new NullPointerException("planOrder");
        this.planOrder = planOrder;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

}
