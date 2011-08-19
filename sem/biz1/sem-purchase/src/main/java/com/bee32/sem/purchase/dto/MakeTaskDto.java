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

    MakeOrderDto order;

    Date deadline;
    List<MakeTaskItemDto> items;

    @Override
    protected void _marshal(MakeTask source) {
        order = mref(MakeOrderDto.class, source.getOrder());

        deadline = source.getDeadline();

        if (selection.contains(ITEMS))
            items = marshalList(MakeTaskItemDto.class, source.getItems());
        else
            items = new ArrayList<MakeTaskItemDto>();
    }

    @Override
    protected void _unmarshalTo(MakeTask target) {
        merge(target, "order", order);

        target.setDeadline(deadline);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
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
        this.items = items;
    }

}
