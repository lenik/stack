package com.bee32.icsf.access;

import org.junit.Assert;
import org.junit.Test;

public class PermissionTest
        extends Assert {

    void testConversion(String mode) {
        Permission p1 = new Permission(mode);
        String s1 = p1.toString();
        Permission p2 = new Permission(s1);
        assertEquals(p1, p2);
        String s2 = p2.toString();
        assertEquals(s1, s2);
    }

    @Test
    public void testFormat() {
        testConversion("r");
        testConversion("Slcdrwx");
        testConversion("xwrdclS");
        testConversion("rwrrwxxx");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadChar() {
        testConversion("%");
    }

}
