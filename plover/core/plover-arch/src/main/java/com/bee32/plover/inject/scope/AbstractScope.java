package com.bee32.plover.inject.scope;

import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public abstract class AbstractScope
        implements Scope {

    protected abstract Map<String, Object> getBeanMap();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> beanMap = getBeanMap();
        Object obj = beanMap.get(name);
        if (obj == null) {
            obj = objectFactory.getObject();
            beanMap.put(name, obj);
        }
        return obj;
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> beanMap = getBeanMap();
        Object obj = beanMap.remove(name);
        return obj;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // Not supported
    }

    @Override
    public Object resolveContextualObject(String key) {
        System.out.println("R ctxobj for key: " + key);
        return null;
    }

}
