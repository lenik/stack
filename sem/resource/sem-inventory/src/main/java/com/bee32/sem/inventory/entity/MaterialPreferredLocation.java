package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.inventory.config.BatchingConfig;

@Entity
public class MaterialPreferredLocation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    String batch;
    StockLocation location;
    String comment;

    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 合成批号（冗余，作为简化自然键结构）
     */
    @Redundant
    @NaturalId
    @Column(length = BatchingConfig.CBATCH_MAXLEN, nullable = false)
    String getCBatch() {
        return computeCanonicalBatch();
    }

    void setCBatch(String cBatch) {
        // Always compute c-batch on the fly.
    }

    protected String computeCanonicalBatch() {
        String batch = getBatch();
        if (batch == null)
            batch = "";
        return batch;
    }

    /**
     * 批号
     */
    @Column(length = BatchingConfig.BATCH_LENGTH)
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

        if (!getCBatch().equals(o.getCBatch()))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += material.hashCode();
        hash += getCBatch().hashCode();
        hash += location.hashCode();
        return hash;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
