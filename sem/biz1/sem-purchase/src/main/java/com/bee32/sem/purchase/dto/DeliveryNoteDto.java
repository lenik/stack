package com.bee32.sem.purchase.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.purchase.entity.DeliveryNote;

public class DeliveryNoteDto
        extends ProcessEntityDto<DeliveryNote> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;

    MakeOrderDto order;
    PartyDto customer;
    Date arrivalDate;
    List<DeliveryNoteItemDto> items;
    DeliveryNoteTakeOutDto takeOut;

    @Override
    protected void _marshal(DeliveryNote source) {
        order = mref(MakeOrderDto.class, source.getOrder());
        customer = mref(PartyDto.class, source.getCustomer());

        arrivalDate = source.getArrivalDate();

        if (selection.contains(ITEMS))
            items = marshalList(DeliveryNoteItemDto.class, source.getItems());
        else
            items = new ArrayList<DeliveryNoteItemDto>();

        takeOut = mref(DeliveryNoteTakeOutDto.class, source.getTakeOut());
    }

    @Override
    protected void _unmarshalTo(DeliveryNote target) {
        merge(target, "order", order);
        merge(target, "customer", customer);

        target.setArrivalDate(arrivalDate);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        merge(target, "takeOut", takeOut);
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

    @Future
    @NotNull
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public List<DeliveryNoteItemDto> getItems() {
        return items;
    }

    public void setItems(List<DeliveryNoteItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        for (DeliveryNoteItemDto item : items)
            item.setParent(this);
        this.items = items;
    }

    public synchronized void addItem(DeliveryNoteItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        item.setParent(this);
        items.add(item);
    }

    public synchronized void removeItem(DeliveryNoteItemDto item) {
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

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        this.customer = customer;
    }

    public DeliveryNoteTakeOutDto getTakeOut() {
        return takeOut;
    }

    public void setTakeOut(DeliveryNoteTakeOutDto takeOut) {
        this.takeOut = takeOut;
    }

    public List<DeliveryNoteTakeOutDto> getTakeOuts() {
        return Arrays.asList(takeOut);
    }
}
