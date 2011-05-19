package com.bee32.plover.orm.entity;

import java.io.File;

import javax.free.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.builtin.PloverConfDto;
import com.bee32.plover.orm.util.hello.dto.HelloMessageDto;
import com.bee32.plover.orm.util.hello.entity.HelloMessage;

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
        assertEquals((Integer) 123, EntityUtil.parseIdOfEntity(BarImpl.class, "123"));
    }

    @Test
    public void testGetDtoTypeSuffix()
            throws ClassNotFoundException {
        Class<?> dtoType = EntityUtil.getDtoType(PloverConf.class);
        assertEquals(PloverConfDto.class, dtoType);
    }

    @Test
    public void testGetDtoTypeNotExist()
            throws ClassNotFoundException {
        Class<?> dtoType = EntityUtil.getDtoType(File.class);
        assertNull(dtoType);
    }

    @Test
    public void testGetDtoTypeAltPackage()
            throws ClassNotFoundException {
        Class<?> dtoType = EntityUtil.getDtoType(HelloMessage.class);
        assertEquals(HelloMessageDto.class, dtoType);
    }

}
