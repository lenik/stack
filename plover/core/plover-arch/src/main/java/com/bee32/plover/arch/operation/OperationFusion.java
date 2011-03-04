package com.bee32.plover.arch.operation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OperationFusion
        extends ClassOperationDiscoverer {

    private OperationFusion() {
    }

    @Override
    protected Map<String, IOperation> buildTypeOperationMap(Class<?> type) {
        int count = 0;
        Map<String, IOperation> merged = null;

        for (IOperationDiscoverer discoverer : OperationDiscovererManager.getDiscoverers()) {
            Map<String, IOperation> map = discoverer.getTypeOperations(type);

            if (map == null)
                continue;

            if (map.isEmpty())
                continue;

            switch (count++) {
            case 0:
                merged = map;
                break;
            case 1:
                merged = new HashMap<String, IOperation>(merged);
            default:
                merged.putAll(map);
            }
        }

        if (merged == null)
            merged = Collections.emptyMap();

        return merged;
    }

    @Override
    public Map<String, IOperation> getInstanceOperations(Object instance) {
        int count = 0;
        Map<String, IOperation> merged = null;

        for (IOperationDiscoverer discoverer : OperationDiscovererManager.getDiscoverers()) {
            Map<String, IOperation> map = discoverer.getInstanceOperations(instance);

            if (map == null)
                continue;

            if (map.isEmpty())
                continue;

            switch (count++) {
            case 0:
                merged = map;
                break;
            case 1:
                merged = new HashMap<String, IOperation>(merged);
            default:
                merged.putAll(map);
            }
        }
        return merged;
    }

    public static final OperationFusion instance = new OperationFusion();

    public static OperationFusion getInstance() {
        return instance;
    }

}
