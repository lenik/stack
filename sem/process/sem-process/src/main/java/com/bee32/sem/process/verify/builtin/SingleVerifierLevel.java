package com.bee32.sem.process.verify.builtin;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.collections.map.IRangeMapEntry;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Blue;
import com.bee32.sem.process.verify.VerifyPolicy;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "single_verifier_level_seq", allocationSize = 1)
public class SingleVerifierLevel
        extends CEntityAuto<Integer>
        implements IRangeMapEntry<Long, VerifyPolicy> {

    private static final long serialVersionUID = 1L;

    private SingleVerifierRankedPolicy policy;
    private long limit;
    private VerifyPolicy targetPolicy;

    public SingleVerifierLevel() {
    }

    public SingleVerifierLevel(SingleVerifierRankedPolicy policy, long limit, VerifyPolicy verifyPolicy) {
        if (policy == null)
            throw new NullPointerException("policy");
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy");

        this.policy = policy;
        this.limit = limit;
        this.targetPolicy = verifyPolicy;
    }

X-Population

    // @NaturalId
    @ManyToOne(optional = false)
    public SingleVerifierRankedPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SingleVerifierRankedPolicy policy) {
        if (policy == null)
            throw new NullPointerException("policy");
        this.policy = policy;
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
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(policy), //
                limit);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (policy == null)
            throw new NullPointerException("policy");
        return selectors(//
                selector(prefix + "policy", policy), //
                new Equals(prefix + "limit", limit));
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
