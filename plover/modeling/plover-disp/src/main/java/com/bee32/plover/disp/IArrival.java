package com.bee32.plover.disp;

import java.util.Date;

public interface IArrival {

    /**
     * Get the previous arrival node.
     *
     * @return Parent dispatch context, <code>null</code> if none.
     */
    IArrival getParent();

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
     * Get the target object.
     *
     * @return The dispatched object, only the final target can be <code>null</code>.
     */
    Object getTarget();

    /**
     * Get the last non-<code>null</code> target in the chain.
     *
     * @return <code>null</code> if none.
     */
    Object getLastNonNullTarget();

    /**
     * The latest expires date gathered in the context chain.
     *
     * @return Expires date, <code>null</code> for no-cache.
     */
    Date getExpires();

}