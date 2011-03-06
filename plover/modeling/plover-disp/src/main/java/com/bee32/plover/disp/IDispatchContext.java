package com.bee32.plover.disp;

import java.util.Date;

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
     * Get the first non-<code>null</code> object in the dispatched-chain.
     *
     * @return <code>null</code> if none reached.
     */
    Object getReachedObject();

    /**
     * The latest expires date gathered in the context chain.
     *
     * @return Expires date, <code>null</code> for no-cache.
     */
    Date getExpires();

}