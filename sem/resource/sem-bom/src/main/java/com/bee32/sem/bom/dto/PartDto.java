package com.bee32.sem.bom.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;

public class PartDto
        extends UIEntityDto<Part, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int CHILDREN = 1;
    public static final int XREFS = 2;
    public static final int ALL_MATERIAL = 0x01000000 | CHILDREN;

    PartDto obsolete;

    List<PartItemDto> children;
    List<PartItemDto> xrefs;
    int xrefCount;

    MaterialDto target;

    boolean valid;
    Date validDateFrom;
    Date validDateTo;

    BigDecimal wage;
    BigDecimal otherFee;
    BigDecimal electricityFee;
    BigDecimal equipmentCost;

    Map<MaterialDto, BigDecimal> allMaterial;

    public PartDto() {
        super();
    }

    public PartDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Part source) {
        obsolete = new PartDto().ref(source.getObsolete());

        target = mref(MaterialDto.class, source.getTarget());

        if (selection.contains(CHILDREN))
            children = marshalList(PartItemDto.class, 0, source.getChildren());
        else
            children = new ArrayList<PartItemDto>();

        if (selection.contains(ALL_MATERIAL)) {
            Map<Material, BigDecimal> _allMaterial = source.obtainAllMaterial();
            if (_allMaterial != null) {
                allMaterial = new HashMap<MaterialDto, BigDecimal>();
                for (Material _m : _allMaterial.keySet()) {
                    MaterialDto m = DTOs.mref(MaterialDto.class, _m);
                    allMaterial.put(m, _allMaterial.get(_m));
                }
            }
        } else
            allMaterial = Collections.emptyMap();

        if (selection.contains(XREFS))
            xrefs = marshalList(PartItemDto.class, 0, source.getXrefs());
        else
            xrefs = Collections.emptyList();

        xrefCount = source.getXrefCount();

        valid = source.isValid();
        validDateFrom = source.getValidDateFrom();
        validDateTo = source.getValidDateTo();

        wage = source.getWage();
        otherFee = source.getOtherFee();
        electricityFee = source.getElectricityFee();
        equipmentCost = source.getEquipmentCost();
    }

    @Override
    protected void _unmarshalTo(Part target) {
        merge(target, "obsolete", obsolete);

        merge(target, "target", this.target);

        if (selection.contains(CHILDREN))
            mergeList(target, "children", children);

        if (selection.contains(XREFS))
            mergeList(target, "xrefs", xrefs);

        target.setValid(valid);
        target.setValidDateFrom(validDateFrom);
        target.setValidDateTo(validDateTo);

        target.setWage(wage);
        target.setOtherFee(otherFee);
        target.setElectricityFee(electricityFee);
        target.setEquipmentCost(equipmentCost);
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

    public List<PartItemDto> getXrefs() {
        return xrefs;
    }

    public void setXrefs(List<PartItemDto> xrefs) {
        this.xrefs = xrefs;
    }

    public int getXrefCount() {
        return xrefs.size();
    }

    public void setXrefCount(int xrefCount) {
        this.xrefCount = xrefCount;
    }

    public MaterialDto getTarget() {
        return target;
    }

    public void setTarget(MaterialDto target) {
        this.target = target;
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

    public Map<MaterialDto, BigDecimal> getAllMaterial() {
        return allMaterial;
    }

}
