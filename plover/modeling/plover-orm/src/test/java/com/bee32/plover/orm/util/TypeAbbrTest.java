package com.bee32.plover.orm.util;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

public class TypeAbbrTest
        extends Assert
        implements ITypeAbbrAware {

    @Test
    public void testAbbr() {
        String abbr = ABBR.abbr("a.b.c.Example");
        // System.out.println(abbr);
        assertEquals("Exam_UjbOO", abbr);
    }

    public static void main(String[] args) {
        Map<String, String> map = ABBR.getAbbrMap();
        for (Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
        System.out.printf("Total %d entries.\n", map.size());
    }

}
