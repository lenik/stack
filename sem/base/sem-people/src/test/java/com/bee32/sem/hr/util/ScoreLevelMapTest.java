package com.bee32.sem.hr.util;

import javax.free.DecodeException;

import org.junit.Assert;
import org.junit.Test;

public class ScoreLevelMapTest
        extends Assert {

    ScoreLevelMap ageMap;

    @Test
    public void testEncode() {
        String encoded = ageMap.encode();
        assertEquals("10:Teens\n20:More\n100:Hundreds\n", encoded);
    }

    @Test
    public void testDecode()
            throws DecodeException {
        String data = "10:Teens\n20:More\n100:Hundreds\n";
        ScoreLevelMap map = ScoreLevelMap.decode(data);
        assertEquals(ageMap, map);
    }

    @Test
    public void testInterpolate() {
        assertNull(ageMap.getTarget(9));
        assertEquals("Teens", ageMap.getTarget(10));
        assertEquals("Teens", ageMap.getTarget(11));
        assertEquals("Teens", ageMap.getTarget(19));
        assertEquals("More", ageMap.getTarget(20));
        assertEquals("Hundreds", ageMap.getTarget(100));
        assertEquals("Hundreds", ageMap.getTarget(200));
    }

}
