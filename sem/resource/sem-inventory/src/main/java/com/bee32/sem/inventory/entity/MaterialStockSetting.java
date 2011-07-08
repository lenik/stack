package com.bee32.sem.inventory.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;

@Entity
public class MaterialStockSetting
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockWarehouse warehouse;

    double safetyStock;
    List<StockLocation> locations;

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

    @Column(nullable = false)
    public double getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(double safetyStock) {
        this.safetyStock = safetyStock;
    }

}
