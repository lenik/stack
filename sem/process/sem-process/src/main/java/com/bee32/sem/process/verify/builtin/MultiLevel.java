package com.bee32.sem.process.verify.builtin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.Alias;
import com.bee32.sem.process.verify.ContextClass;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.result.ErrorResult;
import com.bee32.sem.process.verify.result.RejectedResult;
import com.bee32.sem.process.verify.result.UnauthorizedResult;

@ContextClass(IContextLimit.class)
@Entity
@DiscriminatorValue("ML")
@Alias("level")
public class MultiLevel
        extends VerifyPolicy<IContextLimit, AllowState> {

    private static final long serialVersionUID = 1L;

    private Set<MultiLevelRange> ranges;
    private MultiLevelRangeMap rangeMap;

    public MultiLevel() {
        super(AllowState.class);
    }

    /**
     * @return Non-null range set.
     */
    @OneToMany(mappedBy = "multiLevel")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public synchronized Set<MultiLevelRange> getRanges() {
        if (ranges == null) {
            if (rangeMap == null) {
                ranges = new HashSet<MultiLevelRange>();
            } else {
                ranges = convertToRanges(rangeMap);
                rangeMap = null;
            }
        }
        return ranges;
    }

    public synchronized void setRanges(Set<MultiLevelRange> ranges) {
        this.ranges = ranges;
        this.rangeMap = null;
    }

    /**
     * @return Non-<code>null</code> RangeMap.
     */
    @Transient
    public synchronized MultiLevelRangeMap getRangeMap() {
        if (rangeMap == null)
            if (ranges == null) {
                rangeMap = new MultiLevelRangeMap();
            } else {
                rangeMap = convertToRangeMap(ranges);
                ranges = null;
            }
        return rangeMap;
    }

    public synchronized void setRangeMap(MultiLevelRangeMap rangeMap) {
        this.rangeMap = rangeMap;
        this.ranges = null;
    }

    static MultiLevelRangeMap convertToRangeMap(Collection<MultiLevelRange> ranges) {
        MultiLevelRangeMap map = new MultiLevelRangeMap();
        if (ranges != null)
            for (MultiLevelRange range : ranges) {
                long limit = range.getLimit();
                map.put(limit, range);
            }
        return map;
    }

    static Set<MultiLevelRange> convertToRanges(MultiLevelRangeMap map) {
        Set<MultiLevelRange> ranges = new HashSet<MultiLevelRange>();
        ranges.addAll(map.values());
        return ranges;
    }

    public void addRange(long limit, VerifyPolicy<?, ?> verifyPolicy) {
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy for " + getName());

        MultiLevelRange range = new MultiLevelRange(this, limit, verifyPolicy);
        getRangeMap().put(limit, range);
    }

    public boolean removeRange(long limit) {
        return getRangeMap().remove(limit) != null;
    }

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IContextLimit context) {
        long limit = context.getContextLimit();
        return getResponsiblesWithinLimit(limit);
    }

    public Collection<? extends Principal> getResponsiblesWithinLimit(long limit) {
        DummyContextLimit compatibleContext = new DummyContextLimit("dummy", limit);

        Set<Principal> allDeclared = new HashSet<Principal>();
        if (rangeMap == null)
            return null;

        Long ceil = rangeMap.ceilingKey(limit);

        IdentityHashSet markSet = new IdentityHashSet();
        markSet.add(this);

        while (ceil != null) {

            MultiLevelRange range = rangeMap.get(ceil);
            VerifyPolicy<IContextLimit, ?> policy = (VerifyPolicy<IContextLimit, ?>) range.getTargetPolicy();

            // Already scanned, skip to avoid cyclic ref.
            if (!markSet.add(policy))
                continue;

            if (policy instanceof MultiLevel) {
                MultiLevel mlist = (MultiLevel) policy;
                Collection<? extends Principal> subset = mlist.getResponsiblesWithinLimit(limit);
                allDeclared.addAll(subset);

            } else if (compatibleContext.isCompatibleWith(policy)) {
                Collection<? extends Principal> subset = compatibleContext.getDeclaredResponsibles(policy);
                allDeclared.addAll(subset);
            }

            ceil = rangeMap.higherKey(ceil);
        }

        return allDeclared;
    }

    @Override
    public ErrorResult validateState(IContextLimit context, AllowState state) {
        User user = state.getUser();

        if (!user.impliesOneOf(getDeclaredResponsibles(context)))
            return new UnauthorizedResult(user);

        return null;
    }

    @Override
    public ErrorResult checkState(IContextLimit context, AllowState state) {
        if (!state.isAllowed())
            return new RejectedResult(state.getUser(), state.getMessage());

        return null;
    }

}
