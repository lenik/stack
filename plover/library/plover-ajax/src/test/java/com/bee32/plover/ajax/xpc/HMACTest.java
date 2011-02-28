package com.bee32.plover.ajax.xpc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bee32.plover.ajax.xpc.HMAC;

public class HMACTest {

    @Test
    public void testHmacHello() {
        String hmac = HMAC.hmac_utf8("secret", "hello");
        // System.out.println(hmac);
        assertEquals("84d39285ecadbad209585625efc333b9189ffd2a", hmac);
    }

}
