package com.bee32.plover.test;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.inject.cref.Import;

@Import(FooConfig.class)
public class WiredTestCaseTest
        extends WiredTestCase {

    @Inject
    FooBean foo;

    @Test
    public void testAutoScan() {
        assertNotNull(foo);
    }

}
