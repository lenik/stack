package com.bee32.plover.orm.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.builtin.PloverConfDto;
import com.bee32.plover.orm.util.hello.dto.HelloMessageDto;
import com.bee32.plover.orm.util.hello.entity.HelloMessage;

public class DTOsTest {

    @Test
    public void testGetDtoTypeSuffix()
            throws ClassNotFoundException {
        Class<?> dtoType = DTOs.getDtoType(PloverConf.class);
        assertEquals(PloverConfDto.class, dtoType);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testGetDtoTypeNotExist()
            throws ClassNotFoundException {
        DTOs.getDtoType(File.class);
    }

    @Test
    public void testGetDtoTypeAltPackage()
            throws ClassNotFoundException {
        Class<?> dtoType = DTOs.getDtoType(HelloMessage.class);
        assertEquals(HelloMessageDto.class, dtoType);
    }

}
