package com.bee32.sem.process.verify;

import java.util.Collection;

import com.bee32.icsf.principal.Principal;

public class DummyContext<C> {

    private Class<C> contextClass;

    public DummyContext(Class<C> contextClass) {
        if (contextClass == null)
            throw new NullPointerException("contextClass");
        this.contextClass = contextClass;
    }

    public boolean isCompatibleWith(IVerifyPolicy<?, ?> policy) {
        Class<?> requiredContextClass = ContextClassUtil.getContextClass(policy.getClass());

        if (requiredContextClass.isAssignableFrom(contextClass))
            return true;

        return false;
    }

    public <S extends VerifyState> Collection<? extends Principal> getDeclaredResponsibles(IVerifyPolicy<C, S> policy) {
        C context = contextClass.cast(this);
        return policy.getDeclaredResponsibles(context);
    }

}
