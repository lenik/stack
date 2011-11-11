package com.bee32.plover.inject.scope;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.spring.ScopeProxy;

@Component
@PerState
@ScopeProxy(ScopedProxyMode.INTERFACES)
public class StateHintBean
        implements FactoryBean<StateHint> {

    @Override
    public StateHint getObject()
            throws Exception {
        return new StateHint();
    }

    @Override
    public Class<?> getObjectType() {
        return StateHint.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
