package com.bee32.sem.purchase.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.purchase.entity.MaterialPlan;

public class MaterialPlanDto
        extends StockJobDto<MaterialPlan> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PURCHASE_REQUEST = 2;

    MakeTaskDto task;
    List<PlanOrderDto> planOrders;

    String memo;

    List<MaterialPlanItemDto> items;

    PurchaseRequestDto purchaseRequest;

    @Override
    protected void _marshal(MaterialPlan source) {
        task = mref(MakeTaskDto.class, source.getTask());

        if (selection.contains(ORDERS)) {
            //int orderSelection = selection.translate(ITEMS, StockOrderDto.ITEMS);
            planOrders = mrefList(PlanOrderDto.class, source.getPlanOrders());
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
            mergeList(target, "planOrders", planOrders);

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

    public List<PlanOrderDto> getPlanOrders() {
        return planOrders;
    }

    public void setPlanOrders(List<PlanOrderDto> planOrders) {
        this.planOrders = planOrders;
    }

    @NLength(max = MaterialPlan.MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = TextUtil.normalizeSpace(memo);
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

    /**
     * XXX What's this?
     */
    public String getIdAsString() {
        return getId().toString();
    }

}
