package com.bee32.plover.test;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.context.annotation.Import;

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
