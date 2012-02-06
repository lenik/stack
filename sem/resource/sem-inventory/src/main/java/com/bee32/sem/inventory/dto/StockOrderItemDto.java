package com.bee32.sem.inventory.dto;

import java.io.Serializable;
import java.util.Date;

import javax.free.IllegalUsageException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.entity.StockItemState;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.world.thing.AbstractOrderItemDto;

public class StockOrderItemDto
        extends AbstractOrderItemDto<StockOrderItem>
        implements IEnclosedObject<StockOrderDto> {

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

    public StockOrderItemDto(int fmask) {
        super(fmask);
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
        if (parent == null || parent.isNull()) {
            return false;
            // throw new IllegalStateException("Parent isn't set.");
        }

        StockOrderSubject subject = parent.getSubject();
        return subject.isNegative();
    }

    @Override
    protected void _marshal(StockOrderItem source) {
        StockOrder _parent = source.getParent();

        boolean tryBest = false;
        if (tryBest) {
            Class<?> parentClass = _parent == null ? StockOrder.class : _parent.getClass();
            Class<? extends StockOrderDto> parentDtoType = (Class<? extends StockOrderDto>) EntityUtil
                    .getDtoType(parentClass);
            parent = mref(parentDtoType, _parent);
        } else {
            parent = mref(StockOrderDto.class, _parent);
        }

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

    @Override
    public StockOrderDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(StockOrderDto enclosingObject) {
        setParent(enclosingObject);
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
