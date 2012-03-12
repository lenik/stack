package com.bee32.sem.makebiz.dto;

import java.io.Serializable;
import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.makebiz.entity.DeliveryNoteItem;
import com.bee32.sem.world.thing.AbstractItemDto;

public class DeliveryNoteItemDto
        extends AbstractItemDto<DeliveryNoteItem>
        implements IEnclosedObject<DeliveryNoteDto> {

    private static final long serialVersionUID = 1L;

    DeliveryNoteDto parent;
    PartDto part;
    StockWarehouseDto sourceWarehouse;

    @Override
    protected void _marshal(DeliveryNoteItem source) {
        parent = mref(DeliveryNoteDto.class, source.getParent());
        part = mref(PartDto.class, source.getPart());
        sourceWarehouse = mref(StockWarehouseDto.class, source.getSourceWarehouse());
    }

    @Override
    protected void _unmarshalTo(DeliveryNoteItem target) {
        merge(target, "parent", parent);
        merge(target, "part", part);
        merge(target, "sourceWarehouse", sourceWarehouse);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    public DeliveryNoteDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(DeliveryNoteDto enclosingObject) {
        setParent(enclosingObject);
    }

    public DeliveryNoteDto getParent() {
        return parent;
    }

    public void setParent(DeliveryNoteDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
// if (part == null)
// throw new NullPointerException("part");
        this.part = part;
    }

    public StockWarehouseDto getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(StockWarehouseDto sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(part));
    }

}
