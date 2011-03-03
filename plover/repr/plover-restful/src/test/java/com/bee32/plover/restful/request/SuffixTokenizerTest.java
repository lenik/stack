package com.bee32.plover.restful.request;

import org.junit.Assert;
import org.junit.Test;

public class SuffixTokenizerTest
        extends Assert {

    @Test
    public void testRegular() {
        String str = "lead*b~cc.ddd";
        SuffixTokenizer tokens = new SuffixTokenizer(str);
        assertEquals("*b", tokens.next());
        assertEquals("~cc", tokens.next());
        assertEquals(".ddd", tokens.next());
        assertFalse(tokens.hasNext());
    }

    @Test
    public void testRegularWithLead() {
        String str = "lead*b~cc.ddd";
        SuffixTokenizer tokens = new SuffixTokenizer(str, true);
        assertEquals("lead", tokens.next());
        assertEquals("*b", tokens.next());
        assertEquals("~cc", tokens.next());
        assertEquals(".ddd", tokens.next());
        assertFalse(tokens.hasNext());
    }

    @Test
    public void testAllSingles() {
        String str = "*~.";
        SuffixTokenizer tokens = new SuffixTokenizer(str);
        assertEquals("*", tokens.next());
        assertEquals("~", tokens.next());
        assertEquals(".", tokens.next());
        assertFalse(tokens.hasNext());
    }

    @Test
    public void testAllSinglesWithLead() {
        String str = "*~.";
        SuffixTokenizer tokens = new SuffixTokenizer(str, true);
        assertEquals("", tokens.next());
        assertEquals("*", tokens.next());
        assertEquals("~", tokens.next());
        assertEquals(".", tokens.next());
        assertFalse(tokens.hasNext());
    }

}
