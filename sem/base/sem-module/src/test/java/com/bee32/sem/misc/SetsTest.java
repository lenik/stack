package com.bee32.sem.misc;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

public class SetsTest {

    @Test
    public void testEmptySet() {
        Set<Object> set = Sets.newSet();
        assertEquals(0, set.size());
    }

    @Test
    public void testSingleSet() {
        Set<String> set = Sets.newSet("hello");
        assertEquals(1, set.size());
    }

    @Test
    public void testDuplicates() {
        Set<String> set = Sets.newSet("hello", "hello");
        assertEquals(1, set.size());
    }

    @Test
    public void testUniques() {
        Set<String> set = Sets.newSet("hello", "world");
        assertEquals(2, set.size());
    }

}
