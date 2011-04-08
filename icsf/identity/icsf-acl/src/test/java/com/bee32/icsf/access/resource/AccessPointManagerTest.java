package com.bee32.icsf.access.resource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bee32.icsf.access.annotation.TestService;

public class AccessPointManagerTest
        extends Assert {

    static AccessPointManager cd = new AccessPointManager();

    @BeforeClass
    public static void scan() {
        cd.scanAnnotatedPoints(TestService.class);
    }

    @Test
    public void testAnnotation() {
        AccessPoint method1 = AccessPoint.getInstance("TestService.method1");
        assertNotNull(method1);
        assertEquals("First", method1.getAppearance().getDisplayName());

        AccessPoint method2 = AccessPoint.getInstance("TestService.method2");
        assertNull(method2);

        AccessPoint foo = AccessPoint.getInstance("TestService.foo");
        assertNotNull(foo);
        assertEquals("Foo Action", foo.getAppearance().getDisplayName());
    }

    @Test
    public void listAccessPoints() {
        for (AccessPoint point : AccessPoint.getInstances())
            System.out.println("Access point: " + point);
    }

}
