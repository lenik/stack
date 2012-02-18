package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.util.Date;

import javax.free.ParseException;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.purchase.entity.MakeOrderItem;
import com.bee32.sem.world.thing.AbstractItemDto;

public class MakeOrderItemDto
        extends AbstractItemDto<MakeOrderItem>
        implements IEnclosedObject<MakeOrderDto> {

    private static final long serialVersionUID = 1L;

    MakeOrderDto parent;
    PartDto part;
    Date deadline;

    String externalProductName;
    String externalSpecification;

    @Override
    protected void _marshal(MakeOrderItem source) {
        parent = mref(MakeOrderDto.class, source.getParent());
        part = mref(PartDto.class, source.getPart());
        deadline = source.getDeadline();
        externalProductName = source.getExternalProductName();
        externalSpecification = source.getExternalSpecification();
    }

    @Override
    protected void _unmarshalTo(MakeOrderItem target) {
        merge(target, "parent", parent);
        merge(target, "part", part);
        target.setDeadline(deadline);
        target.setExternalProductName(externalProductName);
        target.setExternalSpecification(externalSpecification);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    public MakeOrderDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(MakeOrderDto enclosingObject) {
        setParent(enclosingObject);
    }

    public MakeOrderDto getParent() {
        return parent;
    }

    public void setParent(MakeOrderDto parent) {
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
//        if (part == null)
//            throw new NullPointerException("part");
        this.part = part;
    }

    @Future
    @NotNull
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @NLength(max = MakeOrderItem.EXT_PROD_NAME_LENGTH)
    public String getExternalProductName() {
        return externalProductName;
    }

    public void setExternalProductName(String externalProductName) {
        this.externalProductName = TextUtil.normalizeSpace(externalProductName);
    }

    @NLength(max = MakeOrderItem.EXT_SPEC_LENGTH)
    public String getExternalSpecification() {
        return externalSpecification;
    }

    public void setExternalSpecification(String externalSpecification) {
        this.externalSpecification = TextUtil.normalizeSpace(externalSpecification);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(part));
    }

}
