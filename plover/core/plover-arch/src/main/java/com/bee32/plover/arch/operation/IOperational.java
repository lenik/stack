package com.bee32.plover.arch.operation;

import java.util.Collection;

public interface IOperational {

    /**
     * Get operation with a specific name.
     *
     * @return <code>null</code> if the operation with specified name doesn't exist.
     */
    IOperation getOperation(String name);

    /**
     * Get all operations.
     *
     * @return Non-<code>null</code> collection of operation objects.
     */
    Collection<IOperation> getOperations();

}
