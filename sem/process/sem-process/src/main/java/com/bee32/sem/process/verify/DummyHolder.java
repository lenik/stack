package com.bee32.sem.process.verify;

import java.util.Collection;

import com.bee32.icsf.principal.Principal;

/**
 * 在寻找责任人时，需要对某一策略遍历。遍历的过程中需要上下文变量的参与。
 *
 * 在这一过程中，上下文变量的定义域比较中，使用本类型辅助进行。
 */
public class DummyHolder<C extends IVerifyContext> {

    private Class<C> contextClass;

    public DummyHolder(Class<C> contextClass) {
        if (contextClass == null)
            throw new NullPointerException("contextClass");
        this.contextClass = contextClass;
    }

    public boolean isCompatibleWith(IVerifyPolicy<?> policy) {
        Class<?> requiredContextClass = ContextClassUtil.getContextClass(policy.getClass());

        if (requiredContextClass.isAssignableFrom(contextClass))
            return true;

        return false;
    }

    public Collection<? extends Principal> getDeclaredResponsibles(IVerifyPolicy<C> policy) {
        C context = contextClass.cast(this);
        return policy.getDeclaredResponsibles(context);
    }

}
