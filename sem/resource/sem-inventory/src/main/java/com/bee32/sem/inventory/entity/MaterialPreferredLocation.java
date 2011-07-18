package com.bee32.sem.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class MaterialPreferredLocation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockLocation location;

    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @NaturalId
    @ManyToOne
    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        this.location = location;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        MaterialPreferredLocation o = (MaterialPreferredLocation) other;

        if (!material.equals(o.material))
            return false;

        if (!location.equals(o.location))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += material.hashCode();
        hash += location.hashCode();
        return hash;
    }

}
