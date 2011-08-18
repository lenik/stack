package com.bee32.sems.bom.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sems.bom.entity.Part;

public class PartDto
        extends UIEntityDto<Part, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int CHILDREN = 1;

    PartDto obsolete;

    List<PartItemDto> children;

    boolean valid;
    Date validDateFrom;
    Date validDateTo;

    BigDecimal wage;
    BigDecimal otherFee;
    BigDecimal electricityFee;
    BigDecimal equipmentCost;

    UserDto creator;

    public PartDto() {
        super();
    }

    public PartDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Part source) {
        obsolete = new PartDto().ref(source.getObsolete());

        if (selection.contains(CHILDREN))
            children = marshalList(PartItemDto.class, 0, source.getChildren());
        else
            children = new ArrayList<PartItemDto>();

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
    protected void _unmarshalTo(Part target) {
        merge(target, "obsolete", obsolete);

        if (selection.contains(CHILDREN))
            mergeList(target, "children", children);

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

    public PartDto getObsolete() {
        return obsolete;
    }

    public void setObsolete(PartDto obsolete) {
        this.obsolete = obsolete;
    }

    public List<PartItemDto> getChildren() {
        return children;
    }

    public void setChildren(List<PartItemDto> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    public boolean addChild(PartItemDto child) {
        if (child == null)
            throw new NullPointerException("child");
        if (children.contains(child))
            return false;
        children.add(child);
        return true;
    }

    public boolean removeChild(PartItemDto child) {
        if (child == null)
            throw new NullPointerException("child");
        return children.remove(child);
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

}
