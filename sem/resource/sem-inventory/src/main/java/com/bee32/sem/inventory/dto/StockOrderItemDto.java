package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.StockItemState;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.world.thing.AbstractOrderItemDto;

public class StockOrderItemDto
        extends AbstractOrderItemDto<StockOrderItem> {

    private static final long serialVersionUID = 1L;

    StockOrderDto order;
    MaterialDto material;
    String batch;
    StockLocationDto location;
    StockItemState state;

    @Override
    protected void _marshal(StockOrderItem source) {
        order = mref(StockOrderDto.class, source.getOrder());
        material = mref(MaterialDto.class, source.getMaterial());
        batch = source.getBatch();
        location = mref(StockLocationDto.class, source.getLocation());
        state = source.getState();
    }

    @Override
    protected void _unmarshalTo(StockOrderItem target) {
        merge(target, "order", order);
        merge(target, "material", material);
        target.setBatch(batch);
        merge(target, "location", location);
        target.setState(state);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        map.getString("batch");
        map.getString("state");
    }

    public StockOrderDto getOrder() {
        return order;
    }

    public void setOrder(StockOrderDto order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCBatch() {
        return computeCanonicalBatch();
    }

    protected String computeCanonicalBatch() {
        String batch = getBatch();
        if (batch == null)
            batch = "";
        return batch;
    }

    public StockLocationDto getLocation() {
        return location;
    }

    public void setLocation(StockLocationDto location) {
        this.location = location;
    }

    public StockItemState getState() {
        return state;
    }

    public void setState(StockItemState state) {
        if (state == null)
            throw new NullPointerException("state");
        this.state = state;
    }

    @Override
    protected Boolean naturalEquals(EntityDto<StockOrderItem, Long> other) {
        StockOrderItemDto o = (StockOrderItemDto) other;

        if (!order.equals(o.order))
            return false;

        if (!material.equals(o.material))
            return false;

        if (!getCBatch().equals(o.getCBatch()))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += order.hashCode();
        hash += material.hashCode();
        hash += getCBatch().hashCode();
        return hash;
    }

}
