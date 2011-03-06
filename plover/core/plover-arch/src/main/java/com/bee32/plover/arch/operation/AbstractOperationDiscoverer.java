package com.bee32.plover.arch.operation;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractOperationDiscoverer
        implements IOperationDiscoverer {

    @Override
    public int getPriority() {
        return 0;
    }

    /**
     * Returns the merged operation map contains both type operations and instance operations.
     *
     * @return A mutable (copied) map.
     */
    @Override
    public Map<String, IOperation> getOperations(Object instance) {
        if (instance == null)
            throw new NullPointerException("instance");

        Map<String, IOperation> allOperations = null;

        Map<String, IOperation> instanceOperations = getInstanceOperations(instance);
        if (instanceOperations != null && !instanceOperations.isEmpty())
            allOperations = instanceOperations;

        Class<?> type = (Class<?>) instance.getClass();

        Map<String, IOperation> typeOperations = getTypeOperations(type);
        if (typeOperations != null && !typeOperations.isEmpty()) {
            if (allOperations == null)
                allOperations = typeOperations;
            else
                for (Map.Entry<String, IOperation> entry : typeOperations.entrySet()) {
                    String typeOperationName = entry.getKey();
                    if (!allOperations.containsKey(typeOperationName)) {
                        IOperation typeOperation = entry.getValue();
                        allOperations.put(typeOperationName, typeOperation);
                    }
                }
        }

        if (allOperations == null)
            allOperations = new HashMap<String, IOperation>();

        return allOperations;
    }

    public IOperation getOperation(Object instance, String operationName) {
        if (instance == null)
            throw new NullPointerException("instance");
        if (operationName == null)
            throw new NullPointerException("operationName");

        Map<String, IOperation> instanceOperations = getInstanceOperations(instance);
        if (instanceOperations != null) {
            IOperation operation = instanceOperations.get(operationName);
            if (operation != null)
                return operation;
        }

        Class<?> instanceClass = (Class<?>) instance.getClass();

        Map<String, IOperation> typeOperations = getTypeOperations(instanceClass);
        if (typeOperations != null) {
            IOperation operation = typeOperations.get(operationName);
            if (operation != null)
                return operation;
        }

        return null;
    }

}
