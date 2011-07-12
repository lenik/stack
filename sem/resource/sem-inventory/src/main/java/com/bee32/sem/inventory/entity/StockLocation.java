package com.bee32.sem.inventory.entity;

import static com.bee32.plover.orm.ext.config.DecimalConfig.QTY_ITEM_PRECISION;
import static com.bee32.plover.orm.ext.config.DecimalConfig.QTY_ITEM_SCALE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.tree.TreeEntityAuto;
import com.bee32.sem.world.thing.Unit;

/**
 * 库位。
 * <p>
 * Usage:
 * <ul>
 * <li>Unrelated regions could be disconnected.
 * </ul>
 */
@Entity
public class StockLocation
        extends TreeEntityAuto<Integer, StockLocation> {

    private static final long serialVersionUID = 1L;

    StockWarehouse warehouse;
    String address;
    double x;
    double y;
    double z;

    BigDecimal capacity;
    Unit capacityUnit = Unit.CUBIC_METER;

    /**
     * 所属仓库
     */
    @ManyToOne(optional = false)
    public StockWarehouse getWarehouse() {
        return warehouse;
    }

    /**
     * 所属仓库
     */
    public void setWarehouse(StockWarehouse warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        this.warehouse = warehouse;
    }

    /**
     * 局部地址
     */
    @Column(length = 30)
    public String getAddress() {
        return address;
    }

    /**
     * 局部地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 库位 X 位置信息
     */
    @Column(nullable = false)
    public double getX() {
        return x;
    }

    /**
     * 库位 X 位置信息
     */

    public void setX(double x) {
        this.x = x;
    }

    /**
     * 库位 Y 位置信息
     */
    @Column(nullable = false)
    public double getY() {
        return y;
    }

    /**
     * 库位 Y 位置信息
     */

    public void setY(double y) {
        this.y = y;
    }

    /**
     * 库位 Z 位置信息
     */
    @Column(nullable = false)
    public double getZ() {
        return z;
    }

    /**
     * 库位 Z 位置信息
     */

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * 容量
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION)
    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    /**
     * 容量单位
     */
    @ManyToOne(optional = false)
    public Unit getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(Unit capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

}
