package com.bee32.plover.ajax;

import org.junit.Assert;
import org.junit.Test;

public class SuccessOrFailureTest
        extends Assert {

    @Test
    public void testSuccess() {
        String got = new SuccessOrFailure("okay") {
            @Override
            protected void service()
                    throws Exception {
            }
        }.dump();
        assertTrue(got.contains("okay"));
    }

    @Test
    public void testFailure() {
        String got = new SuccessOrFailure("okay") {
            @Override
            protected void service()
                    throws Exception {
                throw new Exception("ERROR!");
            }
        }.dump();
        assertFalse(got.contains("okay"));
        assertTrue(got.contains("ERROR!"));
    }

}
