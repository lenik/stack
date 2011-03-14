package com.bee32.sem.process.verify.builtin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IdentityHashSet;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.icsf.principal.Principal;
import com.bee32.sem.process.verify.ContextClass;
import com.bee32.sem.process.verify.VerifyPolicy;

@ContextClass(IContextLimit.class)
@Entity
@DiscriminatorValue("level")
public class MultiLevel
        extends VerifyPolicy<IContextLimit, AllowState> {

    private static final long serialVersionUID = 1L;

    private final TreeMap<Long, VerifyPolicy<?, ?>> levelMap;

    public MultiLevel() {
        super(AllowState.class);
        this.levelMap = new TreeMap<Long, VerifyPolicy<?, ?>>();
    }

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IContextLimit context) {
        long limit = context.getContextLimit();
        return getDeclaredResponsibles(context, limit);
    }

    public Collection<? extends Principal> getDeclaredResponsibles(IContextLimit context, long limit) {
        Set<Principal> allDeclared = new HashSet<Principal>();
        Long ceil = levelMap.ceilingKey(limit);

        IdentityHashSet markSet = new IdentityHashSet();
        markSet.add(this);

        while (ceil != null) {

            @SuppressWarnings("unchecked")
            VerifyPolicy<IContextLimit, ?> policy = (VerifyPolicy<IContextLimit, ?>) levelMap.get(ceil);

            // Already scanned, skip to avoid cyclic ref.
            if (!markSet.add(policy))
                continue;

            if (policy instanceof MultiLevel) {
                MultiLevel mlist = (MultiLevel) policy;
                Collection<? extends Principal> subset = mlist.getDeclaredResponsibles(context, limit);
                allDeclared.addAll(subset);

            } else {

                Collection<? extends Principal> subset = policy.getDeclaredResponsibles(context);
                allDeclared.addAll(subset);
            }

            ceil = levelMap.higherKey(ceil);
        }

        return allDeclared;
    }

    public void add(long limit, VerifyPolicy<?, ?> policy) {
        if (policy == null)
            throw new NullPointerException("allowList");

        levelMap.put(limit, policy);
    }

    @Override
    public String checkState(IContextLimit context, AllowState state) {

        return null;
    }

}
