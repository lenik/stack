package com.bee32.sem.inventory.dto;

import java.io.Serializable;
import java.util.Date;

import javax.free.IllegalUsageException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.StockItemState;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.world.thing.AbstractOrderItemDto;

public class StockOrderItemDto
        extends AbstractOrderItemDto<StockOrderItem> {

    private static final long serialVersionUID = 1L;

    StockOrderDto parent;
    MaterialDto material;
    String batch;
    Date expirationDate;
    StockLocationDto location;
    StockItemState state;

    public StockOrderItemDto() {
        super();
    }

    public StockOrderItemDto(int selection) {
        super(selection);
    }

    @Override
    public StockOrderItemDto populate(Object source) {
        if (source instanceof StockOrderItemDto) {
            StockOrderItemDto o = (StockOrderItemDto) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(StockOrderItemDto o) {
        super._populate(o);
        parent = o.parent;
        material = o.material;
        batch = o.batch;
        expirationDate = o.expirationDate;
        location = o.location;
        state = o.state;
    }

    @Override
    protected boolean isNegated() {
        StockOrderDto parent = getParent();
        if (parent == null || parent.isNull())
            return false;

        StockOrderSubject subject = parent.getSubject();
        return subject.isNegative();
    }

    @Override
    protected void _marshal(StockOrderItem source) {
        parent = mref(StockOrderDto.class, source.getParent());
        material = mref(MaterialDto.class, source.getMaterial());
        batch = source.getBatch();
        expirationDate = source.getExpirationDate();
        location = mref(StockLocationDto.class, source.getLocation());
        state = source.getState();
    }

    @Override
    protected void _unmarshalTo(StockOrderItem target) {
        if (parent.isNull())
            throw new IllegalUsageException("StockOrderItem.parent isn't set");

        merge(target, "parent", parent);
        merge(target, "material", material);
        target.setBatch(batch);
        target.setExpirationDate(expirationDate);
        merge(target, "location", location);
        target.setState(state);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        map.getString("batch");
        map.getString("state");
    }

    public StockOrderDto getParent() {
        return parent;
    }

    public void setParent(StockOrderDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
    protected Serializable naturalId() {
        return new IdComposite(naturalId(parent), naturalId(material), getCBatch());
    }

}
