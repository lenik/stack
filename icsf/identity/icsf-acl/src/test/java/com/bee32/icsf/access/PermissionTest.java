package com.bee32.icsf.access;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

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
        testConversion("sucdrwx");
        testConversion("xwrdcus");
        testConversion("rwrrwxxx");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadChar() {
        testConversion("%");
    }

    public static void main(String[] args)
            throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(Permission.class);
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println("Property: " + pd.getName());
            System.out.println("    get: " + pd.getReadMethod());
            System.out.println("    set: " + pd.getWriteMethod());
        }
    }

}
