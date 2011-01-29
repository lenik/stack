package com.bee32.plover.disp;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.disp.util.ITokenQueue;

/**
 * URI-style path dispatcher.
 * <p>
 * Plover-dispatcher supersedes the Stapler dispatcher.
 *
 * @see org.kohsuke.stapler.Dispatcher
 */
public interface IDispatcher
        extends IComponent {

    /**
     * The order of the dispatcher.
     *
     * Dispatcher with higher order is took first.
     *
     * @return An integer represents the order of the dispatch. A lower value means higher order.
     */
    int getOrder();

    /**
     * Resolve the tokens with-in the context object.
     *
     * @param context
     *            The context object in which this dispatcher runs into, should non-
     *            <code>null</code>.
     * @param tokens
     *            Tokens to be consumed by dispatcher. Only effective dispatch could consume the
     *            corresponding token.
     * @return The final receiver be dispatched into. <code>null</code> if no more matching items.
     * @throws NullPointerException
     *             If either <code>context</code> or <code>tokens</code> is <code>null</code>.
     */
    Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException;

}
