package com.bee32.sems.bom.dto;

import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.material.dto.MaterialDTO;
import com.bee32.sems.org.dto.PersonDTO;

public class ProductDTO extends EntityDto<Product, Long> {

    private static final long serialVersionUID = 1L;

    private PersonDTO creator;
    private PersonDTO updateBy;
    private Date date;
    private Date validDateFrom;
    private Date validDateTo;
    private MaterialDTO material;
    private ProductDTO history;
    private Double wage;
    private Double otherFee;
    private Double electricityFee;
    private Double equipmentCost;


    public ProductDTO() {
        super();
    }

    public ProductDTO(Product source) {
        super(source);
    }


    @Override
    protected void _marshal(Product source) {
        creator = new PersonDTO(source.getCreator());
        updateBy = new PersonDTO(source.getUpdateBy());
        date = source.getDate();
        validDateFrom = source.getValidDateFrom();
        validDateTo = source.getValidDateTo();
        material = new MaterialDTO(source.getMaterial());
        history = new ProductDTO(source.getHistory());
        wage = source.getWage();
        otherFee = source.getOtherFee();
        electricityFee = source.getOtherFee();
        equipmentCost = source.getEquipmentCost();
    }

    @Override
    protected void _unmarshalTo(Product target) {
        throw new NotImplementedException();
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public PersonDTO getCreator() {
        return creator;
    }

    public void setCreator(PersonDTO creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProductDTO getHistory() {
        return history;
    }

    public void setHistory(ProductDTO history) {
        this.history = history;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public PersonDTO getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(PersonDTO updateBy) {
        this.updateBy = updateBy;
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

    public Double getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(Double electricityFee) {
        this.electricityFee = electricityFee;
    }

    public Double getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(Double equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }
}
