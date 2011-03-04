package com.bee32.plover.disp;

import java.util.Date;
import java.util.Map;

import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationFusion;

public interface IDispatchContext {

    /**
     * The parent dispatch context.
     *
     * @return Parent dispatch context, <code>null</code> if none.
     */
    IDispatchContext getParent();

    /**
     * The partial path tokens consumed by this context.
     *
     * @return Non-empty string array contains of the consumed path tokens.
     */
    String[] getConsumedTokens();

    /**
     * The partial path consumed by this context.
     *
     * @return Non-<code>null</code> consumed path.
     */
    String getConsumedPath();

    /**
     * The dispatched object.
     *
     * @return The dispatched object, <code>null</code> if the final object is dispatched to
     *         <code>null</code>.
     */
    Object getObject();

    /**
     * The latest expires date gathered in the context chain.
     *
     * @return Expires date, <code>null</code> for no-cache.
     */
    Date getExpires();

    /**
     * Get the exported operations for the dispatched {@link #getObject() object}.
     *
     * @return Operation map, <code>null</code> if not available, or the dispatched object is
     *         <code>null</code>.
     * @see {@link OperationFusion#getOperations(Object)}
     */
    Map<String, IOperation> getOperations();

    /**
     * Get the named operation.
     *
     * @return <code>null</code> If the operation with specific name isn't existed.
     */
    IOperation getOperation(String name);

}