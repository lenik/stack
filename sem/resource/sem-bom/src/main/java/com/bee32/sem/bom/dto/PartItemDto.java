package com.bee32.sem.bom.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.bom.entity.PartItem;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.MaterialType;

public class PartItemDto
        extends UIEntityDto<PartItem, Long>
        implements IEnclosedObject<PartDto> {

    private static final long serialVersionUID = 1L;

    public static final int PARENT = 1;

    PartDto parent;

    PartDto part;
    MaterialDto material;

    BigDecimal quantity;

    boolean valid;
    Date validDateFrom;
    Date validDateTo;

    @Override
    protected void _marshal(PartItem source) {
        if (selection.contains(PARENT))
            parent = mref(PartDto.class, source.getParent());

        part = mref(PartDto.class, source.getPart());
        material = mref(MaterialDto.class, source.getMaterial());

        quantity = source.getQuantity();

        valid = source.isValid();
        validDateFrom = source.getValidDateFrom();
        validDateTo = source.getValidDateTo();
    }

    @Override
    protected void _unmarshalTo(PartItem target) {
        if (selection.contains(PARENT))
            merge(target, "parent", parent);

        merge(target, "part", part);
        merge(target, "material", material);

        target.setQuantity(quantity);

        target.setValid(valid);
        target.setValidDateFrom(validDateFrom);
        target.setValidDateTo(validDateTo);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public PartDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(PartDto enclosingObject) {
        setParent(enclosingObject);
    }

    public PartDto getParent() {
        return parent;
    }

    public void setParent(PartDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        this.part = part;
        this.material = new MaterialDto().ref();
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
        this.part = new PartDto().ref();
    }

    public MaterialDto getAnyMaterial() {
        if (part != null && !part.isNull())
            return part.getTarget();
        else
            return material;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Date getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Date validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public Date getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Date validDateTo) {
        this.validDateTo = validDateTo;
    }

    public MaterialType getType() {
        return part.isNil() ? MaterialType.RAW : MaterialType.SEMI;
    }

}
