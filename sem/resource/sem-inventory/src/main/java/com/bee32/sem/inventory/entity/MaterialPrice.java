package com.bee32.sem.inventory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.types.MCValue;

/**
 * 物料的基准价格（随时间变化）。
 */
@Entity
public class MaterialPrice
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    Date sinceDate = new Date();
    MCValue price = new MCValue();
    String comment;

    /**
     * 物料
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 价格有效起始时间。
     */
    @NaturalId
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }

    /**
     * 价格
     */
    @Embedded
    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

    /**
     * 备注
     */
    @Column(length = 100)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        MaterialPrice o = (MaterialPrice) other;

        if (!material.equals(o.material))
            return false;

        if (!sinceDate.equals(o.sinceDate))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += material.hashCode();
        hash += sinceDate.hashCode();
        return hash;
    }

}
