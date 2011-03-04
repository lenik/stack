package com.bee32.plover.arch.operation;

import javax.free.INegotiation;

/**
 * Operation abstraction.
 * <p>
 * The operation feature is used in plover-dispatch for verb representation.
 */
public interface IOperation {

    /**
     * Get the name of the operation.
     *
     * @return Non-empty operation name.
     */
    String getName();

    /**
     * Explicit execution mode
     *
     * @return The return value of the operation.
     * @throws NullPointerException
     *             If <code>negotiation</code> is <code>null</code>.
     * @throws Exception
     *             Any exception thrown from the implementation.
     */
    Object execute(Object instance, Object... parameters)
            throws Exception;

    /**
     * Execution with parameters automaticlly matched.
     *
     * @return The return value of the operation.
     * @throws NullPointerException
     *             If <code>negotiation</code> is <code>null</code>.
     * @throws Exception
     *             Any exception thrown from the implementation.
     */
    Object execute(Object instance, INegotiation negotiation)
            throws Exception;

}
