package com.bee32.plover.collections;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class VarargsTest
        extends Assert {

    @Test
    public void testEmptySet() {
        Set<Object> set = Varargs.toSet();
        assertEquals(0, set.size());
    }

    @Test
    public void testSingleSet() {
        Set<String> set = Varargs.toSet("hello");
        assertEquals(1, set.size());
    }

    @Test
    public void testDuplicates() {
        Set<String> set = Varargs.toSet("hello", "hello");
        assertEquals(1, set.size());
    }

    @Test
    public void testUniques() {
        Set<String> set = Varargs.toSet("hello", "world");
        assertEquals(2, set.size());
    }

}
