package com.bee32.plover.arch.operation;

import java.util.Map;
import java.util.ServiceLoader;

/**
 * Discover operation from classes and instances.
 *
 * The discoverer are configured using java service framework.
 *
 * @see OperationDiscovererManager
 * @see ServiceLoader
 */
public interface IOperationDiscoverer {

    int getPriority();

    /**
     * The class operations.
     *
     * @return Non-<code>null</code> operations map for the class.
     */
    Map<String, IOperation> getTypeOperations(Class<?> type);

    /**
     * Ths instance operations should not overlap with class operations.
     * <p>
     * If the same name operation is existed for the instance, the instance operation hide the class
     * one.
     *
     * @return Operation map for the instance only, <code>null</code> if none.
     */
    Map<String, IOperation> getInstanceOperations(Object instance);

    /**
     * Returns the merged operation map contains both type operations and instance operations.
     *
     * @return A mutable (copied) map.
     */
    Map<String, IOperation> getOperations(Object instance);

}
