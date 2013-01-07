package com.bee32.sem.makebiz.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.makebiz.entity.MakeOrderItem;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.world.thing.AbstractItemDto;
import com.bee32.sem.world.thing.UnitDto;

public class MakeOrderItemDto
        extends AbstractItemDto<MakeOrderItem>
        implements IEnclosedObject<MakeOrderDto> {

    private static final long serialVersionUID = 1L;

    public static final int MATERIAL_ATTRIBUTES = 1;

    MakeOrderDto parent;
    MaterialDto material;
    Date deadline;

    boolean nameplate;

    String externalProductName;
    String externalModelSpec;
    String externalUnit;

    List<DeliveryNoteItemDto> deliveryNoteItems;

    @Override
    protected void _marshal(MakeOrderItem source) {
        parent = mref(MakeOrderDto.class, source.getParent());

        int materialSelection = 0;
        if (selection.contains(MATERIAL_ATTRIBUTES))
            materialSelection = MATERIAL_ATTRIBUTES;
        material = mref(MaterialDto.class, //
                materialSelection, source.getMaterial());

        deadline = source.getDeadline();
        nameplate = source.isNameplate();
        externalProductName = source.getExternalProductName();
        externalModelSpec = source.getExternalModelSpec();
        externalUnit = source.getExternalUnit();
        deliveryNoteItems = mrefList(DeliveryNoteItemDto.class, source.getDeliveryNoteItems());
    }

    @Override
    protected void _unmarshalTo(MakeOrderItem target) {
        merge(target, "parent", parent);
        merge(target, "material", material);
        target.setDeadline(deadline);
        target.setExternalProductName(externalProductName);
        target.setExternalModelSpec(externalModelSpec);
        target.setExternalUnit(externalUnit);
        target.setNameplate(nameplate);
        mergeList(target, "deliveryNoteItems", deliveryNoteItems);
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

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

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
    public String getExternalModelSpec() {
        return externalModelSpec;
    }

    public void setExternalModelSpec(String externalModelSpec) {
        this.externalModelSpec = TextUtil.normalizeSpace(externalModelSpec);
    }

    @NLength(max = MakeOrderItem.EXT_UNIT_LENGTH)
    public String getExternalUnit() {
        if (externalUnit == null || externalUnit.isEmpty()) {
            UnitDto unit = material.getUnit();
            if (unit == null)
                return null;
            else
                return unit.getLabel();
        } else
            return externalUnit;
    }

    public void setExternalUnit(String externalUnit) {
        this.externalUnit = externalUnit;
    }

    public boolean isNameplate() {
        return nameplate;
    }

    public void setNameplate(boolean nameplate) {
        this.nameplate = nameplate;
    }

    public List<DeliveryNoteItemDto> getDeliveryNoteItems() {
        return deliveryNoteItems;
    }

    public void setDeliveryNoteItems(List<DeliveryNoteItemDto> deliveryNoteItems) {
        this.deliveryNoteItems = deliveryNoteItems;
    }

    public BigDecimal getDeliveriedQuantity() {
        BigDecimal num = new BigDecimal(0);
        for (DeliveryNoteItemDto deliveryNoteItem : deliveryNoteItems) {
            num = num.add(deliveryNoteItem.getQuantity());
        }

        return num;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(material));
    }

}
