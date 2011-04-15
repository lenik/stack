package com.bee32.plover.orm.util;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.orm.feaCat.Tiger;

/**
 * @see DataTransferObject
 */
public class DataTransferObjectTest
        extends Assert {

    @Test
    public void testConvertSingle() {
        Tiger tigro = new Tiger("Tigro", "Yellow");
        TigerDto dto = new TigerDto(tigro);
        Tiger tigro1 = dto.unmarshal();
        assertEquals(tigro, tigro1);
    }

    @Test
    public void testMarshalList() {
        Tiger a = new Tiger("a", "x");
        Tiger b = new Tiger("b", "y");
        Tiger[] tigers = { a, b };

        List<TigerDto> tigerDtos = TigerDto.marshalList(TigerDto.class, tigers);
        Iterator<TigerDto> it = tigerDtos.iterator();
        TigerDto aDto = it.next();
        assertEquals("a", aDto.getName());

        TigerDto bDto = it.next();
        assertEquals("y", bDto.getColor());
    }

}
