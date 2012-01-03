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

}
