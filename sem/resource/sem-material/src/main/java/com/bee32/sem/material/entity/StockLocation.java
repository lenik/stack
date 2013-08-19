package com.bee32.sem.material.entity;

import static com.bee32.plover.ox1.config.DecimalConfig.QTY_ITEM_PRECISION;
import static com.bee32.plover.ox1.config.DecimalConfig.QTY_ITEM_SCALE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.tree.TreeEntityAuto;
import com.bee32.sem.world.thing.Unit;

/**
 * 库位
 *
 * 在某空间划分下，${tr.inventory.warehouse}中的位置，可以按树形组织。
 *
 * <p lang="en">
 * Stock Location
 *
 * <p>
 * Usage:
 * <ul>
 * <li>Unrelated regions could be disconnected.
 * </ul>
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_location_seq", allocationSize = 1)
public class StockLocation
        extends TreeEntityAuto<Integer, StockLocation> {

    private static final long serialVersionUID = 1L;

    public static final int CAPACITY_UNIT_HINT_LENGTH = 30;

    StockWarehouse warehouse;
    double x;
    double y;
    double z;

    BigDecimal capacity;
    Unit capacityUnit;
    String capacityUnitHint;
    int rank;

    public StockLocation() {
    }

    public StockLocation(StockWarehouse warehouse, String label) {
        this.warehouse = warehouse;
        this.label = label;
    }

    public StockLocation(StockWarehouse warehouse, String label, StockLocation parent) {
        super(parent);
        this.warehouse = warehouse;
        this.label = label;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof StockLocation)
            _populate((StockLocation) source);
        else
            super.populate(source);
    }

    protected void _populate(StockLocation o) {
        super._populate(o);
        warehouse = o.warehouse;
        x = o.x;
        y = o.y;
        z = o.z;
        capacity = o.capacity;
        capacityUnit = o.capacityUnit;
        capacityUnitHint = o.capacityUnitHint;
        rank = o.rank;
    }

    /**
     * 所属仓库
     *
     * 库位所在的仓库。
     */
    @ManyToOne(optional = false)
    public StockWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouse warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        this.warehouse = warehouse;
    }

    /**
     * X坐标
     *
     * 库位 X 位置信息。
     */
    @Column(nullable = false)
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    /**
     * Y坐标
     *
     * 库位 Y 位置信息。
     */
    @Column(nullable = false)
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Z坐标
     *
     * 库位 Z 位置信息。
     */
    @Column(nullable = false)
    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * 容量
     *
     * 库位的容量，根据所存放的物料种类，容量单位可能不同。
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
     *
     * 库位容量的单位，根据所存放的物料种类，容量单位可能不同。
     */
    @ManyToOne
    public Unit getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(Unit capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

    /**
     * 单位提示
     *
     * 容量的单位提示（如“长度”、“容积”等）。
     */
    @Column(length = CAPACITY_UNIT_HINT_LENGTH)
    public String getCapacityUnitHint() {
        return capacityUnitHint;
    }

    public void setCapacityUnitHint(String capacityUnitHint) {
        this.capacityUnitHint = capacityUnitHint;
    }

    /**
     * 等级
     *
     * 库位等级。
     */
    @Column(nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
