package com.bee32.plover.arch.operation;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.TreeSet;

public class OperationDiscovererManager {

    private static TreeSet<IOperationDiscoverer> discoverers;

    static void refreshDiscoverers() {
        discoverers = new TreeSet<IOperationDiscoverer>(OperationDiscovererComparator.getInstance());

        ServiceLoader<IOperationDiscoverer> loader = ServiceLoader.load(IOperationDiscoverer.class);
        for (IOperationDiscoverer discoverer : loader)
            discoverers.add(discoverer);
    }

    static {
        refreshDiscoverers();
    }

    public static Collection<IOperationDiscoverer> getDiscoverers() {
        return discoverers;
    }

    static {
        // XXX - cyclic loading?
        // ModuleLoader.load();
    }

}
