package com.bee32.plover.arch.util;

import org.junit.Assert;
import org.junit.Test;

public class Flags32Test
        extends Assert {

    @Test
    public void testBitsStringZero() {
        Flags32 f = new Flags32();
        assertEquals("0", f.toString());
    }

    @Test
    public void testBitsStringF0() {
        Flags32 f = new Flags32();
        f.checkAndLoad(0xf0);
        assertEquals("11110000", f.toString());
    }

    @Test
    public void testCheckReload1() {
        Flags32 f = new Flags32();
        assertTrue(f.checkAndLoad(1));
        assertFalse(f.checkAndLoad(1));
    }

    @Test
    public void testCheckReloadOverlap() {
        Flags32 f = new Flags32();
        assertTrue(f.checkAndLoad(3));
        assertFalse(f.checkAndLoad(1));
        assertFalse(f.checkAndLoad(3));
    }

}
