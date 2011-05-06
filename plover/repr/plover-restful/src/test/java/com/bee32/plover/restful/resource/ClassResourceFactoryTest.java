package com.bee32.plover.restful.resource;

import org.junit.Assert;
import org.junit.Test;

public class ClassResourceFactoryTest
        extends Assert {

    @Test
    public void testGetSclClassFile()
            throws ResourceResolveException {
        ClassResourceFactory crf = new ClassResourceFactory();
        IResource res = crf.resolve("scl/java/lang/String.class");
        assertNotNull(res);
    }

    @Test
    public void testGetThisClassFile()
            throws ResourceResolveException {
        ClassResourceFactory crf = new ClassResourceFactory();
        Class<?> c = ClassResourceFactoryTest.class;

        String contextClass = c.getName().replace('.', '/');
        String path = c.getSimpleName() + ".class";

        crf.resolve("scl/java/lang/String.class");
    }

    /**
     * scl/com/bee32/... com/bee32/...
     *
     */
}
