package com.bee32.plover.inject.cref;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class StdScanContextTest
        extends Assert {

    @Test
    public void testImportAnnotations() {
        ContextRef cref = ContextRefs.SCAN_STD;

        Resource[] resources = cref.getConfigResources();

        assertTrue(resources.length >= 1);
    }

}
