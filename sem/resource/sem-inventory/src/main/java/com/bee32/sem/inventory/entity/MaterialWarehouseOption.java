package com.bee32.sem.inventory.entity;

import static com.bee32.plover.orm.ext.config.DecimalConfig.QTY_ITEM_PRECISION;
import static com.bee32.plover.orm.ext.config.DecimalConfig.QTY_ITEM_SCALE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;

/**
 * 物料的仓库选项
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_warehouse_option_seq", allocationSize = 1)
public class MaterialWarehouseOption
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockWarehouse warehouse;
    BigDecimal safetyStock = new BigDecimal(1);
    int stkPeriod = 365;

    /**
     * 物料
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    /**
     * 仓库
     */
    @NaturalId
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
     * 该仓库中的安全库存数量。默认为1。
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(BigDecimal safetyStock) {
        this.safetyStock = safetyStock;
    }

    /**
     * 循环盘点周期 （天数）
     *
     * 默认为1年。
     */
    @Column(nullable = false)
    public int getStkPeriod() {
        return stkPeriod;
    }

    public void setStkPeriod(int stkPeriod) {
        this.stkPeriod = stkPeriod;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        MaterialWarehouseOption o = (MaterialWarehouseOption) other;

        if (material == null || warehouse == null)
            return false;

        if (!material.equals(o.material))
            return false;

        if (!warehouse.equals(o.warehouse))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        if (material == null || warehouse == null)
            return System.identityHashCode(this);
        hash += material.hashCode();
        hash += warehouse.hashCode();
        return hash;
    }

}
