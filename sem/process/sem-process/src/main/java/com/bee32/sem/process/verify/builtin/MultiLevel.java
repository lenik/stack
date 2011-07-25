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
public class MultiLevel
        extends EntityAuto<Integer>
        implements IRangeMapEntry<Long, VerifyPolicy> {

    private static final long serialVersionUID = 1L;

    private MultiLevelPolicy policy;
    private long limit;
    private VerifyPolicy targetPolicy;

    public MultiLevel() {
    }

    public MultiLevel(MultiLevelPolicy multiLevel, long limit, VerifyPolicy verifyPolicy) {
        if (multiLevel == null)
            throw new NullPointerException("multiLevel");
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy");

        this.policy = multiLevel;
        this.limit = limit;
        this.targetPolicy = verifyPolicy;
    }

    // @NaturalId
    @ManyToOne(optional = false)
    public MultiLevelPolicy getMultiLevel() {
        return policy;
    }

    public void setMultiLevel(MultiLevelPolicy multiLevel) {
        this.policy = multiLevel;
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
        MultiLevel o = (MultiLevel) other;

        if (limit != o.limit)
            return false;

        if (!Nullables.equals(policy, o.policy))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = new Long(limit).hashCode();

        if (policy != null)
            hash += policy.getId().hashCode();

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
