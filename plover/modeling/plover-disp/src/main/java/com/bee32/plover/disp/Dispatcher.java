package com.bee32.plover.disp;

import java.util.ServiceLoader;
import java.util.TreeSet;

import com.bee32.plover.disp.util.ITokenQueue;

/**
 * The dispatcher facade. This is also a {@link IDispatcher}, though it needn't be.
 * <p>
 * This facade is commonly used by HttpServlet or Filter, which accepts the initial URL prefix, and
 * pass down the rest of the tokens.
 */
public class Dispatcher
        extends AbstractDispatcher {

    TreeSet<IDispatcher> dispatchers;

    // private DispatchTree tree;

    public Dispatcher() {
        reloadProviders();
    }

    synchronized void reloadProviders() {
        dispatchers = new TreeSet<IDispatcher>(DispatcherComparator.getInstance());

        ServiceLoader<IDispatcher> dispatcherLoader;
        dispatcherLoader = ServiceLoader.load(IDispatcher.class);

        for (IDispatcher dispatcher : dispatcherLoader) {
            dispatchers.add(dispatcher);
        }
    }

    private static int maxDispatches = 100;

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (context == null)
            throw new NullPointerException("context");

        int count = 0;

        while (!tokens.isEmpty()) {
            boolean processed = false;
            for (IDispatcher dispatcher : dispatchers) {
                Object next = dispatcher.dispatch(context, tokens);
                if (next != null) {
                    context = next;
                    processed = true;
                    break;
                }
            }
            if (!processed)
                break;
            if (++count > maxDispatches)
                throw new DispatchException(String.format("Dispatch-deadloop (%d) detected.", maxDispatches));
        }

        return context;
    }

    private static final Dispatcher instance = new Dispatcher();

    public static Dispatcher getInstance() {
        return instance;
    }

}
