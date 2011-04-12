package com.bee32.plover.orm.util;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.bee32.plover.orm.feaCat.Tiger;

public class DataTransferObjectTest {

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
