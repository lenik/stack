package com.bee32.plover.arch.naming;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReverseLookupRegistryTest
        extends NamedNodeTestBase {

    ReverseLookupRegistry registry;

    public ReverseLookupRegistryTest() {
        registry = ReverseLookupRegistry.getInstance();
    }

    @Test
    public void lookupApple() {
        assertEquals("life/food/apple", registry.getLocation("Apple"));
    }

    @Test
    public void lookupBasketBall() {
        assertEquals("life/sport/basket", registry.getLocation("BasketBall"));
    }

    @Test
    public void lookupRed() {
        assertEquals("gen/color/red", registry.getLocation("Red"));
    }

    @Test
    public void lookupWeak() {
        assertEquals("gen/power/weak", registry.getLocation("Weak"));
    }

}
