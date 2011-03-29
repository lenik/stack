package com.bee32.icsf.access.annotation;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.icsf.access.builtins.PointPermission;

public class CheckpointDiscovererTest
        extends Assert {

    @Test
    public void testAnnotation() {
        CheckpointDiscoverer cd = new CheckpointDiscoverer();
        cd.scanAnnotatedPoints(TestService.class);

        PointPermission method1 = PointPermission.getInstance("TestService.method1");
        assertNotNull(method1);
        assertEquals("First", method1.getAppearance().getDisplayName());

        PointPermission method2 = PointPermission.getInstance("TestService.method2");
        assertNull(method2);

        PointPermission foo = PointPermission.getInstance("TestService.foo");
        assertNotNull(foo);
        assertEquals("Foo Action", foo.getAppearance().getDisplayName());
    }

}
