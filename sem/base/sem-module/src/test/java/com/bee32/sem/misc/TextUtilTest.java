package com.bee32.sem.misc;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.util.TextUtil;

public class TextUtilTest
        extends Assert {

    @Test
    public void testNormSpace1() {
        String s = "  hello, \n   world!  ";
        String t = TextUtil.normalizeSpace(s);
        assertEquals("hello, world!", t);
    }

}
