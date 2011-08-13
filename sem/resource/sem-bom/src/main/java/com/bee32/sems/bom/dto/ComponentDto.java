package com.bee32.sems.bom.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sems.bom.entity.Component;

public class ComponentDto
        extends TreeEntityDto<Component, Long, ComponentDto> {

    private static final long serialVersionUID = 1L;

    ComponentDto obsolete;

    MaterialDto material;
    BigDecimal quantity;

    boolean valid;
    Date validDateFrom;
    Date validDateTo;

    BigDecimal wage;
    BigDecimal otherFee;
    BigDecimal electricityFee;
    BigDecimal equipmentCost;

    UserDto creator;

    public ComponentDto() {
        super();
    }

    public ComponentDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Component source) {
        obsolete = new ComponentDto().ref(source.getObsolete());

        material = mref(MaterialDto.class, source.getMaterial());
        quantity = source.getQuantity();

        valid = source.isValid();
        validDateFrom = source.getValidDateFrom();
        validDateTo = source.getValidDateTo();

        wage = source.getWage();
        otherFee = source.getOtherFee();
        electricityFee = source.getElectricityFee();
        equipmentCost = source.getEquipmentCost();

        creator = mref(UserDto.class, source.getCreator());
    }

    @Override
    protected void _unmarshalTo(Component target) {
        merge(target, "obsolete", obsolete);
        merge(target, "material", material);
        target.setQuantity(quantity);

        target.setValid(valid);
        target.setValidDateFrom(validDateFrom);
        target.setValidDateTo(validDateTo);

        target.setWage(wage);
        target.setOtherFee(otherFee);
        target.setElectricityFee(electricityFee);
        target.setEquipmentCost(equipmentCost);

        merge(target, "creator", creator);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public ComponentDto getObsolete() {
        return obsolete;
    }

    public void setObsolete(ComponentDto obsolete) {
        this.obsolete = obsolete;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
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

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        if (wage == null)
            throw new NullPointerException("wage");
        this.wage = wage;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        if (otherFee == null)
            throw new NullPointerException("otherFee");
        this.otherFee = otherFee;
    }

    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        if (electricityFee == null)
            throw new NullPointerException("electricityFee");
        this.electricityFee = electricityFee;
    }

    public BigDecimal getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(BigDecimal equipmentCost) {
        if (equipmentCost == null)
            throw new NullPointerException("equipmentCost");
        this.equipmentCost = equipmentCost;
    }

    public BigDecimal getPricePart() {
        BigDecimal total = new BigDecimal(0);
        total = total.add(wage);
        total = total.add(otherFee);
        total = total.add(electricityFee);
        total = total.add(equipmentCost);
        return total;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(getParent()), //
                naturalId(material));
    }

}
