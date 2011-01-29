package com.bee32.plover.disp.util;

import org.junit.Assert;
import org.junit.Test;

public class TokenQueueTest
        extends Assert {

    @Test
    public void testParsePath() {
        TokenQueue queue = new TokenQueue("a/b/c");
        assertEquals("a", queue.shift());
        assertEquals("b", queue.shift());
        assertEquals("c", queue.shift());
        assertTrue(queue.isEmpty());
        assertNull(queue.shift());
    }

    @Test
    public void testParsePathEmptyMidlets() {
        TokenQueue queue = new TokenQueue("///////a");
        assertEquals("a", queue.shift());
    }

    @Test
    public void testParsePathTrailingSlash() {
        TokenQueue queue = new TokenQueue("a/");
        assertEquals("a", queue.shift());
        assertEquals(ITokenQueue.INDEX, queue.shift());
        assertNull(queue.shift());
    }

    @Test
    public void testParsePathTrailingSlashes() {
        TokenQueue queue = new TokenQueue("a//////");
        assertEquals("a", queue.shift());
        assertEquals(ITokenQueue.INDEX, queue.shift());
        assertNull(queue.shift());
    }

    @Test
    public void testParsePathEmpty() {
        TokenQueue queue = new TokenQueue("");
        assertNull(queue.shift());

        queue = new TokenQueue("////");
        assertEquals(ITokenQueue.INDEX, queue.shift());
    }

}
