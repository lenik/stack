package com.bee32.sem.purchase.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.purchase.entity.MakeTask;

public class MakeTaskDto
        extends TxEntityDto<MakeTask> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PLANS = 2;

    MakeOrderDto order;

    Date deadline;
    List<MakeTaskItemDto> items;

    List<MaterialPlanDto> plans;

    @Override
    protected void _marshal(MakeTask source) {
        order = mref(MakeOrderDto.class, source.getOrder());

        deadline = source.getDeadline();

        if (selection.contains(ITEMS))
            items = marshalList(MakeTaskItemDto.class, source.getItems());
        else
            items = new ArrayList<MakeTaskItemDto>();

        if (selection.contains(PLANS))
            plans = marshalList(MaterialPlanDto.class, source.getPlans());
        else
            plans = new ArrayList<MaterialPlanDto>();
    }

    @Override
    protected void _unmarshalTo(MakeTask target) {
        merge(target, "order", order);

        target.setDeadline(deadline);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        if (selection.contains(PLANS))
            mergeList(target, "plans", plans);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public MakeOrderDto getOrder() {
        return order;
    }

    public void setOrder(MakeOrderDto order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<MakeTaskItemDto> getItems() {
        return items;
    }

    public void setItems(List<MakeTaskItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        for (MakeTaskItemDto item : items)
            item.setTask(this);
        this.items = items;
    }

    public synchronized void addItem(MakeTaskItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        item.setTask(this);
        items.add(item);
    }

    public synchronized void removeItem(MakeTaskItemDto item) {
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
        if (plans == null)
            throw new NullPointerException("plans");
        for (MaterialPlanDto plan : plans)
            plan.setTask(this);
        this.plans = plans;
    }

}
