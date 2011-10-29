package com.bee32.plover.inject.scope;

import org.springframework.beans.factory.ObjectFactory;

public class StateScope
        extends AbstractScope {

    StateManager stateManager = StateManager.getGlobalStateManager();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        StateContext context = stateManager.getCurrentContext();
        Object obj = context.getAttribute(name);
        if (obj == null) {
            obj = objectFactory.getObject();
            context.setAttribute(name, obj);
        }
        return obj;
    }

    @Override
    public Object remove(String name) {
        StateContext context = stateManager.getCurrentContext();
        Object obj = context.removeAttribute(name);
        return obj;
    }

}
