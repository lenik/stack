package com.bee32.plover.arch.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AutoNamingTest {

    static abstract class AbstractCat {
    }

    static class BlackCat
            extends AbstractCat {
    }

    static class BigBlackCat
            extends BlackCat {
    }

    @Test
    public void testLevel1() {
        assertEquals("black", AutoNaming.getAutoName(BlackCat.class));
    }

    @Test
    public void testLevel2() {
        assertEquals("big-black", AutoNaming.getAutoName(BigBlackCat.class));
    }

}
