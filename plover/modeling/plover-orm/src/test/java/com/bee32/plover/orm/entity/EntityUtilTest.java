package com.bee32.plover.orm.entity;

import javax.free.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class EntityUtilTest
        extends Assert {

    @Test
    public void testGetTypeEnum() {
        assertEquals(KeyTypeEnum.INT, EntityUtil.getTypeEnum(Integer.class));
        assertEquals(KeyTypeEnum.LONG, EntityUtil.getTypeEnum(Long.class));
        assertEquals(KeyTypeEnum.STRING, EntityUtil.getTypeEnum(String.class));
    }

    @Test
    public void testGetType() {
        assertEquals(Integer.class, EntityUtil.getKeyType(BarImpl.class));
    }

    @Test
    public void testParseId()
            throws ParseException {
        assertEquals((Integer) 123, EntityUtil.parseId(BarImpl.class, "123"));
    }

}
