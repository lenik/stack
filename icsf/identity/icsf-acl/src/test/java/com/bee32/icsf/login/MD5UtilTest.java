package com.bee32.icsf.login;

import org.junit.Assert;
import org.junit.Test;

public class MD5UtilTest
        extends Assert {

    @Test
    public void testEmpty() {
        String emptyMd5 = MD5Util.md5("");
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", emptyMd5);
    }

    @Test
    public void testHello() {
        String helloMd5 = MD5Util.md5("hello");
        assertEquals("5d41402abc4b2a76b9719d911017c592", helloMd5);
    }

}
