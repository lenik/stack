package com.bee32.plover.inject;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class SimpleContainerTest
        extends Assert {

    @Test
    public void testRequireSameClass()
            throws ContextException {
        SimpleContainer container = new SimpleContainer();

        String hello = "hello";
        File file = new File("xyz/def");

        container.registerContext(String.class, hello);
        container.registerContext(File.class, file);

        assertEquals(hello, container.require(String.class));
        assertEquals(file, container.require(File.class));
    }

    @Test
    public void testRequireSameClassQualifierInheritance()
            throws ContextException {
        SimpleContainer container = new SimpleContainer();

        String hello = "hello";
        File file = new File("xyz/def");

        container.registerContext(String.class, "a", hello);
        container.registerContext(File.class, "a.b.c", file);

        assertEquals(hello, container.require(String.class, "a.b"));
        assertNull(container.query(File.class, "a.b"));
    }

}
