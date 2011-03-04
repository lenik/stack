package com.bee32.plover.arch.operation;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.operation.builtin.MethodOperationDiscoverer;
import com.bee32.plover.arch.operation.builtin.OverlayOperationDiscoverer;

public class OperationDiscovererManagerTest
        extends Assert {

    @Test
    public void testProviders() {
        Set<Class<?>> discovererClasses = new HashSet<Class<?>>();
        for (IOperationDiscoverer discoverer : OperationDiscovererManager.getDiscoverers()) {
            discovererClasses.add(discoverer.getClass());
        }

        assertTrue(discovererClasses.contains(MethodOperationDiscoverer.class));
        assertTrue(discovererClasses.contains(OverlayOperationDiscoverer.class));
    }

}
