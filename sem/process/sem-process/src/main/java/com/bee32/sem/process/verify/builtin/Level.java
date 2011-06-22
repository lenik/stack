package com.bee32.sem.process.verify.builtin;

import javax.free.Nullables;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bee32.plover.collections.map.IRangeMapEntry;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.sem.process.verify.VerifyPolicy;

@Entity
@Blue
public class Level
        extends EntityAuto<Integer>
        implements IRangeMapEntry<Long, VerifyPolicy> {

    private static final long serialVersionUID = 1L;

    private MultiLevel multiLevel;
    private long limit;
    private VerifyPolicy targetPolicy;

    public Level() {
    }

    public Level(MultiLevel multiLevel, long limit, VerifyPolicy verifyPolicy) {
        if (multiLevel == null)
            throw new NullPointerException("multiLevel");
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy");

        this.multiLevel = multiLevel;
        this.limit = limit;
        this.targetPolicy = verifyPolicy;
    }

    // @NaturalId
    @ManyToOne(optional = false)
    public MultiLevel getMultiLevel() {
        return multiLevel;
    }

    public void setMultiLevel(MultiLevel multiLevel) {
        this.multiLevel = multiLevel;
    }

    /**
     * Fix: HQL keyword not work in &#64;OrderBy clause.
     */
    // @NaturalId
    long getLimit_() {
        return limit;
    }

    void setLimit_(long limit) {
        this.limit = limit;
    }

    @Transient
    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    @ManyToOne(optional = false)
    public VerifyPolicy getTargetPolicy() {
        return targetPolicy;
    }

    public void setTargetPolicy(VerifyPolicy verifyPolicy) {
        this.targetPolicy = verifyPolicy;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Integer> other) {
        Level o = (Level) other;

        if (limit != o.limit)
            return false;

        if (!Nullables.equals(multiLevel, o.multiLevel))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = new Long(limit).hashCode();

        if (multiLevel != null)
            hash += multiLevel.getId().hashCode();

        return hash;
    }

    // --o IRangeMapEntry

    /**
     * The same as {@link #getLimit()}.
     */
    @Transient
    @Override
    public Long getX() {
        return limit;
    }

    /**
     * The same as {@link #getTargetPolicy()}.
     */
    @Transient
    @Override
    public VerifyPolicy getTarget() {
        return targetPolicy;
    }

}
