package com.bee32.plover.internet.spam;

import org.junit.Assert;
import org.junit.Test;

public class EmailUtilTest
        extends Assert {

    @Test
    public void testHtmlReverseNull() {
        String reversed = EmailUtil.htmlReverse(null);
        assertNull(reversed);
    }

    @Test
    public void testHtmlReverseNormal() {
        String reversed = EmailUtil.htmlReverse("a@b.c");
        assertTrue(reversed.contains("c.b@a"));
    }

}
