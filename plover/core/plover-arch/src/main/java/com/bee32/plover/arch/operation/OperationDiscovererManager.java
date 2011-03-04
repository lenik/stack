package com.bee32.plover.arch.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

public class OperationDiscovererManager {

    private static Collection<IOperationDiscoverer> discoverers;

    static void refreshDiscoverers() {
        discoverers = new ArrayList<IOperationDiscoverer>();

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

}
