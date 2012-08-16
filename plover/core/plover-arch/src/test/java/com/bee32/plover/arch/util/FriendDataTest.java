package com.bee32.plover.arch.util;

import org.junit.Assert;
import org.junit.Test;

public class FriendDataTest
        extends Assert {

    @Test
    public void test1() {
        String script = FriendData.script(FriendDataTest.class, "1");
        assertEquals("你好\n", script);
    }

}
