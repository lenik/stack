package com.bee32.plover.disp;

import java.util.ServiceLoader;
import java.util.TreeSet;

import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.disp.util.TokenQueue;

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

    public Object dispatch(Object context, String path)
            throws DispatchException {
        TokenQueue tq = new TokenQueue(path);
        return dispatch(context, tq);
    }

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
                throw new DispatchException("Dispatched too many times, is there any dead loop?");
        }

        return context;
    }

    private static final Dispatcher instance = new Dispatcher();

    public static Dispatcher getInstance() {
        return instance;
    }

}
