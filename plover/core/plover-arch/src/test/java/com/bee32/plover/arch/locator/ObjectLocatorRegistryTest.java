package com.bee32.plover.arch.locator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.arch.naming.ReverseLookupRegistry;

public class ObjectLocatorRegistryTest
        extends ObjectLocationTestBase {

    ReverseLookupRegistry registry;

    public ObjectLocatorRegistryTest() {
        // registry = ObjectLocatorRegistry.getInstance();
        // registry.register(foodMap);
        // registry.register(sportsMap);
        // registry.register(lifeHub);
        // registry.register(colorMap);
        // registry.register(powerMap);
        // registry.register(genHub);
        // registry.register(root);
    }

    @Test
    public void testLookup() {
        ReverseLookupRegistry registry = ReverseLookupRegistry.getInstance();
        assertEquals("life/food/apple", registry.getLocation("Apple"));
        assertEquals("life/sport/basket", registry.getLocation("BasketBall"));
        assertEquals("gen/color/red", registry.getLocation("Red"));
        assertEquals("gen/power/weak", registry.getLocation("Weak"));
    }

}
