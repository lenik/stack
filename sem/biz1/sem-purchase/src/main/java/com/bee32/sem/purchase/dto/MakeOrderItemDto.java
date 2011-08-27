package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.purchase.entity.MakeOrderItem;
import com.bee32.sems.bom.dto.PartDto;

public class MakeOrderItemDto
        extends CEntityDto<MakeOrderItem, Long> {

    private static final long serialVersionUID = 1L;

    MakeOrderDto order;
    PartDto part;
    BigDecimal quantity = new BigDecimal(1);

    @Override
    protected void _marshal(MakeOrderItem source) {
        order = mref(MakeOrderDto.class, source.getOrder());
        part = mref(PartDto.class, source.getPart());
        quantity = source.getQuantity();
    }

    @Override
    protected void _unmarshalTo(MakeOrderItem target) {
        merge(target, "order", order);
        merge(target, "part", part);
        target.setQuantity(quantity);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MakeOrderDto getOrder() {
        return order;
    }

    public void setOrder(MakeOrderDto order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        if (part == null)
            throw new NullPointerException("part");
        this.part = part;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(order), //
                naturalId(part));
    }

}
