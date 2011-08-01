package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_preferred_location_seq", allocationSize = 1)
public class MaterialPreferredLocation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockLocation location;
    boolean permanent;

    /**
     * 物料
     */
    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 建议库位。
     */
    @NaturalId
    @ManyToOne
    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        if (location == null)
            throw new NullPointerException("location");
        this.location = location;
    }

    /**
     * 是否永久库存。
     */
    @Column(nullable = false)
    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        MaterialPreferredLocation o = (MaterialPreferredLocation) other;

        if (material == null || location == null)
            return false;

        if (!material.equals(o.material))
            return false;

        if (!location.equals(o.location))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;

        if (material == null || location == null)
            return System.identityHashCode(this);

        hash += material.hashCode();
        hash += location.hashCode();
        return hash;
    }

}
