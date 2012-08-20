package com.bee32.sem.frame.web;

import org.junit.Assert;
import org.junit.Test;

public class SEMVersionBeanTest
        extends Assert {

    @Test
    public void testTwo() {
        SEMVersionBean version = new SEMVersionBean("1.1 234");
        assertEquals("dev", version.getTag());
        assertEquals("234", version.getRelease());
        assertEquals("1.1", version.getVersion());
    }

}
