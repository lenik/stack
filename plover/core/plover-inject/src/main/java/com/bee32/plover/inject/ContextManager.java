package com.bee32.plover.inject;

import javax.free.NotImplementedException;

import com.bee32.plover.inject.util.NameQualifiedClassMap;

public class ContextManager {

    protected final NameQualifiedClassMap contextMap;

    public ContextManager() {
        contextMap = new NameQualifiedClassMap();
    }

    public <T> void registerContext(Class<T> contextClass, T contextInstance) {
        contextMap.put(contextClass, contextInstance);
    }

    public <T> void registerContext(Class<T> contextClass, String qualifier, T contextInstance) {
        contextMap.put(contextClass, qualifier, contextInstance);
    }

    public <T> void registerContext(Class<T> contextClass, Object qualifier, T contextInstance) {
        throw new NotImplementedException();
    }

    public void removeContext(Class<?> contextClass) {
        contextMap.remove(contextClass);
    }

    public void removeContext(Class<?> contextClass, String qualifier) {
        contextMap.remove(contextClass, qualifier);
    }

    public void removeContext(Class<?> contextClass, Object qualifier) {
        throw new NotImplementedException();
    }

}
