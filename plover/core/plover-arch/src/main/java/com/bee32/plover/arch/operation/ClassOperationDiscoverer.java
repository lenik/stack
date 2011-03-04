package com.bee32.plover.arch.operation;

import java.util.HashMap;
import java.util.Map;

public abstract class ClassOperationDiscoverer
        extends AbstractOperationDiscoverer {

    private Map<Class<?>, Map<String, IOperation>> classMap;

    public ClassOperationDiscoverer() {
        classMap = new HashMap<Class<?>, Map<String, IOperation>>();
    }

    protected abstract Map<String, IOperation> buildTypeOperationMap(Class<?> type);

    @Override
    public Map<String, IOperation> getTypeOperations(Class<?> type) {
        Map<String, IOperation> map = classMap.get(type);
        if (map == null) {
            synchronized (this) {
                if (map == null) {
                    map = buildTypeOperationMap(type);
                    if (map == null)
                        throw new NullPointerException("Null map returned from the build method.");
                }
                classMap.put(type, map);
            }
        }
        return map;
    }

    @Override
    public Map<String, IOperation> getInstanceOperations(Object instance) {
        return null;
    }

}
