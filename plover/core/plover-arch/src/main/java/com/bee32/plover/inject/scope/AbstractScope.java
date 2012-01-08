package com.bee32.plover.inject.scope;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public abstract class AbstractScope
        implements Scope, DisposableBean {

    static Logger logger = LoggerFactory.getLogger(AbstractScope.class);

    final Map<String, Runnable> destructionCallbacks = new LinkedHashMap<String, Runnable>();

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
        if (obj != null)
            destructionCallbacks.remove(name);
        return obj;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        destructionCallbacks.put(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        logger.debug("R ctxobj for key: " + key);
        return null;
    }

    @Override
    public void destroy() {
        for (Runnable callback : destructionCallbacks.values()) {
            callback.run();
        }
        destructionCallbacks.clear();
    }

}
