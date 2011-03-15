package com.bee32.plover.orm.entity;

import org.junit.Assert;
import org.junit.Test;

public class EntityFormatTest
        extends Assert {

    @Test
    public void testParse() {
        EntityFormat format = EntityFormat.valueOf("VERBOSE");
        assertEquals(EntityFormat.VERBOSE, format);
    }

}
