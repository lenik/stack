package com.bee32.plover.inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class LegacyContextTest
        extends Assert {

    @Test
    public void testImportAnnotations() {
        LegacyContext context = new LegacyContext();

        Resource[] resources = context.getConfigResources();

        assertEquals(1, resources.length);
    }

}
