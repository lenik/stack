package com.bee32.plover.disp;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;

/**
 * The dispatcher facade. This is also a {@link IDispatcher}, though it needn't be.
 * <p>
 * This facade is commonly used by HttpServlet or Filter, which accepts the initial URL prefix, and
 * pass down the rest of the tokens.
 */
public class Dispatcher
        extends AbstractDispatcher {

    static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

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

    public Set<IDispatcher> getDispatchers() {
        return dispatchers;
    }

    private static int maxDispatches = 100;

    @Override
    public IArrival dispatch(IArrival arrival, ITokenQueue tokens)
            throws DispatchException {
        if (arrival == null)
            throw new NullPointerException("context");

        if (dispatchers.isEmpty())
            logger.warn("No dispatch configured");

        int count = 0;

        logger.debug("Dispatch " + tokens);

        while (!tokens.isEmpty()) {
            boolean processed = false;

            for (IDispatcher dispatcher : dispatchers) {
                IArrival next = dispatcher.dispatch(arrival, tokens);
                if (next != null) {
                    arrival = next;
                    processed = true;
                    break;
                }
            }

            if (!processed)
                break;

            logger.debug("    " + arrival);

            if (++count > maxDispatches)
                throw new DispatchException(String.format("Dispatch-deadloop (%d) detected.", maxDispatches));
        }

        return arrival;
    }

    private static final Dispatcher instance = new Dispatcher();

    public static Dispatcher getInstance() {
        return instance;
    }

}
