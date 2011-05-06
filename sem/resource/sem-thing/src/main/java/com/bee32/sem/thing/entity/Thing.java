package com.bee32.sem.thing.entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.ext.ext.EntityExt;

@MappedSuperclass
public abstract class Thing
        extends EntityExt<Long> {

    private static final long serialVersionUID = 1L;

    String serial;
    String name;
    String description;

    String barCode;

    Unit unit;
    UnitConv unitConv;

    public Thing() {
        super();
    }

    public Thing(String serial, String name) {
        super(name);

        if (serial == null)
            throw new NullPointerException("serial");

        this.serial = serial;
        this.name = name;
    }

    /**
     * 物品编码、物品序列号（唯一）（必填）
     */
    @Column(length = 32, nullable = false)
    public String getSerial() {
        return serial;
    }

    /**
     * 物品编码、物品序列号（唯一）（必填）
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * 物品名称（必填）
     */
    @Column(length = 40, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * 物品名称（必填）
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 物品描述
     */
    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    /**
     * 物品描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 物品条码
     */
    @Column(length = 20)
    public String getBarCode() {
        return barCode;
    }

    /**
     * 物品条码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 物品单位（必填）
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Unit getUnit() {
        return unit;
    }

    /**
     * 物品单位（必填）
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * 物品采用的单位换算表（可选）
     */
    @ManyToOne
    public UnitConv getUnitConv() {
        return unitConv;
    }

    /**
     * 物品采用的单位换算表（可选）
     */
    public void setUnitConv(UnitConv unitConv) {
        this.unitConv = unitConv;
    }

}
