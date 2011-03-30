package com.bee32.sem.process.verify.builtin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.MapKey;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.sem.process.verify.ContextClass;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.result.ErrorResult;
import com.bee32.sem.process.verify.result.RejectedResult;
import com.bee32.sem.process.verify.result.UnauthorizedResult;

@ContextClass(IContextLimit.class)
@Entity
@DiscriminatorValue("LEVEL")
public class MultiLevel
        extends VerifyPolicy<IContextLimit, AllowState> {

    private static final long serialVersionUID = 1L;

    private MultiLevelRanges rangeMap;

    public MultiLevel() {
        super(AllowState.class);
    }

    @OneToMany(mappedBy = "multiLevel")
    // @Cascade(CascadeType.DELETE_ORPHAN)
    @MapKey(columns = @Column(name = "limit"))
    @Sort(type = SortType.NATURAL)
    @Column(name = "verifyPolicy")
    public Map<Long, VerifyPolicy<?, ?>> getRangeMap() {
        return rangeMap;
    }

    public void setRangeMap(Map<Long, VerifyPolicy<?, ?>> rangeMap) {
        this.rangeMap = new MultiLevelRanges(rangeMap);
    }

    public void addRange(long limit, VerifyPolicy<?, ?> verifyPolicy) {
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy for " + getName());

        rangeMap.put(limit, verifyPolicy);
    }

    public boolean removeRange(long limit) {
        VerifyPolicy<?, ?> definedPolicy = rangeMap.remove(limit);
        return definedPolicy != null;
    }

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IContextLimit context) {
        long limit = context.getContextLimit();
        return getResponsiblesWithinLimit(limit);
    }

    public Collection<? extends Principal> getResponsiblesWithinLimit(long limit) {
        DummyContextLimit compatibleContext = new DummyContextLimit("dummy", limit);

        Set<Principal> allDeclared = new HashSet<Principal>();
        Long ceil = rangeMap.ceilingKey(limit);

        IdentityHashSet markSet = new IdentityHashSet();
        markSet.add(this);

        while (ceil != null) {

            VerifyPolicy<IContextLimit, ?> policy = (VerifyPolicy<IContextLimit, ?>) rangeMap.get(ceil);

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
