package com.bee32.sem.purchase.dto;

import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.makebiz.dto.MaterialPlanDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.purchase.entity.PurchaseRequest;

public class PurchaseRequestDto
        extends ProcessEntityDto<PurchaseRequest> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PLANS = 2;
    public static final int TAKE_INS = 4;

    List<PurchaseRequestItemDto> items;
    List<MaterialPlanDto> plans;

    List<PurchaseTakeInDto> takeIns;

    @Override
    protected void _marshal(PurchaseRequest source) {
        if (selection.contains(ITEMS))
            items = marshalList(PurchaseRequestItemDto.class, source.getItems());
        else
            items = Collections.emptyList();

        if (selection.contains(PLANS))
            plans = mrefList(MaterialPlanDto.class, source.getPlans());
        else
            plans = Collections.emptyList();

        if (selection.contains(TAKE_INS))
            takeIns = mrefList(PurchaseTakeInDto.class, PurchaseTakeInDto.ORDER_ITEMS, source.getTakeIns());
        else
            takeIns = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(PurchaseRequest target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        if (selection.contains(PLANS))
            mergeList(target, "plans", plans);

        if (selection.contains(TAKE_INS))
            mergeList(target, "takeIns", takeIns);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<PurchaseRequestItemDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseRequestItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(PurchaseRequestItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(PurchaseRequestItemDto item) {
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

    public List<MaterialPlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlanDto> plans) {
        this.plans = plans;
    }

    public void addPlan(MaterialPlanDto plan) {
        if (plan == null)
            throw new NullPointerException("plan");
        if (!plans.contains(plan))
            plans.add(plan);
    }

    public List<PurchaseTakeInDto> getTakeIns() {
        return takeIns;
    }

    public void setTakeIns(List<PurchaseTakeInDto> takeIns) {
        if (takeIns == null)
            throw new NullPointerException("takeIns");
        this.takeIns = takeIns;
    }

    public synchronized void addTakeIn(PurchaseTakeInDto takeIn) {
        if (takeIn == null)
            throw new NullPointerException("takeIn");
        if (!takeIns.contains(takeIn)) {
            takeIns.add(takeIn);
            takeIn.setPurchaseRequest(this);
        }
    }

    public synchronized void removeTakeIn(PurchaseTakeInDto takeIn) {
        if (takeIn == null)
            throw new NullPointerException("takeIn");
        takeIns.remove(takeIn);
        // orderHolder.detach();
    }

}
