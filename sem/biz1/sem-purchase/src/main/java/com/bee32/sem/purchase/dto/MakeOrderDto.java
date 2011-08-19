package com.bee32.sem.purchase.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.entity.MakeOrder;

public class MakeOrderDto
        extends TxEntityDto<MakeOrder> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int TASKS = 2;

    PartyDto customer;
    Date deadline;
    String status;

    List<MakeOrderItemDto> items;

    List<MakeTaskDto> tasks;

    @Override
    protected void _marshal(MakeOrder source) {
        customer = mref(PartyDto.class, source.getCustomer());
        deadline = source.getDeadline();
        status = source.getStatus();

        if (selection.contains(ITEMS))
            items = marshalList(MakeOrderItemDto.class, source.getItems());
        else
            items = new ArrayList<MakeOrderItemDto>();

        if (selection.contains(TASKS))
            tasks = marshalList(MakeTaskDto.class, source.getTasks());
        else
            tasks = new ArrayList<MakeTaskDto>();
    }

    @Override
    protected void _unmarshalTo(MakeOrder target) {
        merge(target, "customer", customer);
        target.setDeadline(deadline);
        target.setStatus(status);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        if (selection.contains(TASKS))
            mergeList(target, "tasks", tasks);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        if (customer == null)
            throw new NullPointerException("customer");
        this.customer = customer;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MakeOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<MakeOrderItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public List<MakeTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<MakeTaskDto> tasks) {
        if (tasks == null)
            throw new NullPointerException("tasks");
        this.tasks = tasks;
    }

}
