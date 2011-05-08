package com.bee32.plover.arch.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TextMapTest
        extends Assert {

    static Object[] _(Object... values) {
        return values;
    }

    static TextMap all;
    {

        Map<String, Object> _all = new HashMap<String, Object>();

        _all.put("foo", "bar");
        _all.put("it.name", _("lenik", "tom", "lucy"));
        _all.put("it.age", _(19, 20));
        _all.put("it.sex", _('m', 'm', 'f'));

        all = new TextMap(_all);
    }

    @Test
    public void testShift() {
        TextMap[] sa = all.shift("it.");
        assertEquals(3, sa.length);

        TextMap lenik = sa[0];
        assertEquals("lenik", lenik.getRaw("name"));
        assertEquals(19, lenik.getRaw("age"));
        assertEquals('m', lenik.getRaw("sex"));

        TextMap tom = sa[1];
        assertEquals("tom", tom.getRaw("name"));
        assertEquals(20, tom.getRaw("age"));
        assertEquals('m', tom.getRaw("sex"));

        TextMap lucy = sa[2];
        assertEquals("lucy", lucy.getRaw("name"));
        assertNull(lucy.getRaw("age"));
        assertEquals('f', lucy.getRaw("sex"));
    }

    @Test
    public void testShiftNoExist() {
        TextMap[] sa = all.shift("notExisted");
        assertEquals(0, sa.length);
    }

}
