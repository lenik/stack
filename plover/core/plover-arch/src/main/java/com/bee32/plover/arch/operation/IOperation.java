package com.bee32.plover.arch.operation;

import java.util.Map;

import javax.free.INegotiation;

import com.bee32.plover.arch.IComponent;

/**
 * Operation abstraction.
 * <p>
 * The operation feature is used in plover-dispatch for verb representation.
 */
public interface IOperation
        extends IComponent {

    /**
     * Get the name of the operation.
     *
     * @return Non-empty operation name.
     */
    @Override
    String getName();

    /**
     * Execute with simplified named parameters.
     *
     * @param instance
     *            Non-<code>null</code> instance to be operated on.
     * @param context
     *            Non-<code>null</code> named parameter map.
     * @return The return value of the operation.
     * @throws NullPointerException
     *             If <code>parameters</code> is <code>null</code>.
     * @throws Exception
     *             Any exception thrown from the implementation.
     */
    Object execute(Object instance, IOperationContext context)
            throws Exception;

    /**
     * Execute with indexed parameters.
     *
     * @param instance
     *            Non-<code>null</code> instance to be operated on.
     * @param parameters
     *            Non-<code>null</code> indexed parameters.
     * @return The return value of the operation.
     * @throws NullPointerException
     *             If <code>parameters</code> is <code>null</code>.
     * @throws Exception
     *             Any exception thrown from the implementation.
     */
    Object execute(Object instance, Object... parameters)
            throws Exception;

    /**
     * Execute with negotiated parameters.
     *
     * @param instance
     *            Non-<code>null</code> instance to be operated on.
     * @param negotiation
     *            Non-<code>null</code> negotiation object.
     * @return The return value of the operation.
     * @throws NullPointerException
     *             If <code>negotiation</code> is <code>null</code>.
     * @throws Exception
     *             Any exception thrown from the implementation.
     */
    Object execute(Object instance, INegotiation negotiation)
            throws Exception;

    /**
     * Execute with named parameters.
     *
     * @param instance
     *            Non-<code>null</code> instance to be operated on.
     * @param parameters
     *            Non-<code>null</code> named parameter map.
     * @return The return value of the operation.
     * @throws NullPointerException
     *             If <code>parameters</code> is <code>null</code>.
     * @throws Exception
     *             Any exception thrown from the implementation.
     */
    Object execute(Object instance, Map<String, ?> parameters)
            throws Exception;

}
