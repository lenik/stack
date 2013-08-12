package com.bee32.plover.arch.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Label 1
 */
public class ClassNLSTest
        extends Assert {

    @Test
    public void testGetLabel() {
        ClassNLS nls = new ClassNLS(ClassNLSTest.class);
        assertEquals("Label 1", nls.getLabel());
    }

    static class NonExist {
    }

    @Test
    public void testGetLabelNonExisted() {
        ClassNLS nls = new ClassNLS(NonExist.class);
        assertEquals("NonExist", nls.getLabel());
    }

    @Test
    public void testGetPropLabel() {
        ClassNLS nls = new ClassNLS(ClassNLSTest.class);
        String prop1Label = nls.getPropertyLabel("prop1");
        assertEquals("Prop Label 1", prop1Label);
    }

    @Test
    public void testGetPropLabelNonExisted() {
        ClassNLS nls = new ClassNLS(ClassNLSTest.class);
        String prop100Label = nls.getPropertyLabel("prop100");
        assertEquals("prop100", prop100Label);
    }

}
