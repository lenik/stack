package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Pink;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockLocation;

@Entity
@Pink
public class StockTransfer
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockLocation location;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        this.location = location;
    }

}
