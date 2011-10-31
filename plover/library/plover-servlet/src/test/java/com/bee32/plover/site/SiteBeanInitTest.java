package com.bee32.plover.site;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;
import com.bee32.plover.test.WiredTestCase;

/**
 * Test shows:
 * <ul>
 * <li>For singleton scope, Foo is eagerly created.
 * <li>For site scope, Foo is not created.
 * </ul>
 */
public class SiteBeanInitTest
        extends WiredTestCase {

    @Component
    @PerSite
    static class Foo {

        public static int count = 0;

        public Foo() {
            System.out.println("Foo created: " + this);
            count++;
        }

    }

    @Test
    public void testInit() {
        System.out.println("Count = " + Foo.count);
    }

}
