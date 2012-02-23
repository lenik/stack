package com.bee32.sem.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_preferred_location_seq", allocationSize = 1)
public class MaterialPreferredLocation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    StockLocation location;
    boolean permanent;

    public MaterialPreferredLocation() {
    }

    public MaterialPreferredLocation(Material material, StockLocation location) {
        this.material = material;
        this.location = location;
    }

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
    protected Serializable naturalId() {
        return new IdComposite(naturalId(material), naturalId(location));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (material == null)
            throw new NullPointerException("material");
        if (location == null)
            throw new NullPointerException("location");
        return selectors(//
                selector(prefix + "material", material), //
                selector(prefix + "location", location));
    }

    @Override
    protected CEntity<?> owningEntity() {
        return material;
    }

}
