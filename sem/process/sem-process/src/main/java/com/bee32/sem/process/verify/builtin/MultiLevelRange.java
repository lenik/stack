package com.bee32.sem.process.verify.builtin;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.collections.map.IRangeMapEntry;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.VerifyPolicy;

@Entity
public class MultiLevelRange
        extends EntityBean<Integer>
        implements IRangeMapEntry<Long, VerifyPolicy<?, ?>> {

    private static final long serialVersionUID = 1L;

    private MultiLevel multiLevel;
    private long limit;
    private VerifyPolicy<?, ?> targetPolicy;

    public MultiLevelRange() {
    }

    public MultiLevelRange(MultiLevel multiLevel, long limit, VerifyPolicy<?, ?> verifyPolicy) {
        if (multiLevel == null)
            throw new NullPointerException("multiLevel");
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy");

        this.multiLevel = multiLevel;
        this.limit = limit;
        this.targetPolicy = verifyPolicy;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public MultiLevel getMultiLevel() {
        return multiLevel;
    }

    public void setMultiLevel(MultiLevel multiLevel) {
        this.multiLevel = multiLevel;
    }

    @NaturalId
    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    @ManyToOne(optional = false)
    public VerifyPolicy<?, ?> getTargetPolicy() {
        return targetPolicy;
    }

    public void setTargetPolicy(VerifyPolicy<?, ?> verifyPolicy) {
        this.targetPolicy = verifyPolicy;
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
    public VerifyPolicy<?, ?> getTarget() {
        return targetPolicy;
    }

}
