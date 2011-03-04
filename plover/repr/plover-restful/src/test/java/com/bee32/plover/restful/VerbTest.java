package com.bee32.plover.restful;

import org.junit.Assert;
import org.junit.Test;

public class VerbTest
        extends Assert {

    @Test
    public void testTranslate0() {
        Verb verb = new Verb("hello", 0);

        String result = verb.translate("a/b/c");
        assertEquals("a/b/c/hello", result);
    }

    @Test
    public void testTranslate1() {
        Verb verb = new Verb("hello");

        String result = verb.translate("a/b/c");
        assertEquals("a/b/hello/c", result);
    }

    @Test
    public void testTranslate2() {
        Verb verb = new Verb("hello", 2);

        String result = verb.translate("a/b/c");
        assertEquals("a/hello/b/c", result);
    }

    @Test
    public void testTranslate3() {
        Verb verb = new Verb("hello", 3);

        String result = verb.translate("a/b/c");
        assertEquals("hello/a/b/c", result);
    }

    @Test
    public void testTranslate4() {
        Verb verb = new Verb("hello", 4);

        String result = verb.translate("a/b/c");
        assertNull(result);
    }

}
