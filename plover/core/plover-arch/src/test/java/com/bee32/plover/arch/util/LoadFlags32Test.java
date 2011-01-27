package com.bee32.plover.arch.util;

import org.junit.Assert;
import org.junit.Test;

public class LoadFlags32Test
        extends Assert {

    @Test
    public void testBitsStringZero() {
        LoadFlags32 f = new LoadFlags32();
        assertEquals("0", f.toString());
    }

    @Test
    public void testBitsStringF0() {
        LoadFlags32 f = new LoadFlags32();
        f.checkAndLoad(0xf0);
        assertEquals("11110000", f.toString());
    }

    @Test
    public void testCheckReload1() {
        LoadFlags32 f = new LoadFlags32();
        assertTrue(f.checkAndLoad(1));
        assertFalse(f.checkAndLoad(1));
    }

    @Test
    public void testCheckReloadOverlap() {
        LoadFlags32 f = new LoadFlags32();
        assertTrue(f.checkAndLoad(3));
        assertFalse(f.checkAndLoad(1));
        assertFalse(f.checkAndLoad(3));
    }

}
