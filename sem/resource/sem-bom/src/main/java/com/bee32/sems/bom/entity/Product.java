package com.bee32.sems.bom.entity;

import java.util.Date;

import javax.persistence.*;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sems.material.entity.MaterialImpl;
import com.bee32.sems.org.entity.Person;

/**
 * bom对应的产品
 */
@Entity
public class Product extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 创建bom的人
     */
    private Person creator;

    /**
     * 更新人
     */
    private Person updateBy;

    /**
     * bom建立日期
     */
    private Date date;

    /**
     * bom起用日期
     */
    private Date validDateFrom;

    /**
     * bom无效日期
     */
    private Date validDateTo;

    /**
     * 对应物料
     */
    private MaterialImpl material;

    /**
     * 历史bom
     */
    private Product history;

    /**
     * 工资
     */
    private Double wage;

    /**
     * 其他费用
     */
    private Double otherFee;

    /**
     * 电费
     */
    private Double electricityFee;

    /**
     * 设备费
     */
    private Double equipmentCost;

    // need by hibernate
    public Product() {
    }

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    public Person getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Person updateBy) {
        this.updateBy = updateBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Product getHistory() {
        return history;
    }

    public void setHistory(Product history) {
        this.history = history;
    }

    @ManyToOne(targetEntity = MaterialImpl.class, fetch = FetchType.LAZY)
    public MaterialImpl getMaterial() {
        return material;
    }

    public void setMaterial(MaterialImpl material) {
        this.material = material;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Date validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    @Temporal(TemporalType.TIMESTAMP)
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

    @Transient
    public Double getPricePart() {
        return (wage == null ? 0 : wage)
                + (otherFee == null ? 0 : otherFee)
                + (electricityFee == null ? 0 : electricityFee)
                + (equipmentCost == null ? 0 : equipmentCost);
    }
}
