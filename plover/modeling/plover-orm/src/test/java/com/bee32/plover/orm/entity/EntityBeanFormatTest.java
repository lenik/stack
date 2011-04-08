package com.bee32.plover.orm.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.orm.feaCat.Cat;
import com.bee32.plover.util.FormatStyle;

public class EntityBeanFormatTest
        extends Assert {

    Cat kate = new Cat("kate", "yellow");
    Cat kitty = new Cat("kitty", "pink");
    Cat lily = new Cat("lily", "white");

    public EntityBeanFormatTest() {
        kitty.setParent(kate);
        lily.setParent(kate);

        // Using linked-set to keep order for test.
        Set<Cat> children = new LinkedHashSet<Cat>();
        children.add(kitty);
        children.add(lily);
        kate.setChildren(children);
    }

    @Test
    public void testToStringSimple() {
        Cat cat = new Cat("kitty", "pink");

        String catNoid = cat.toString(FormatStyle.SIMPLE);
        assertEquals("kitty@Cat", catNoid);

        EntityAccessor.setId(cat, 10L);
        EntityAccessor.setVersion(cat, 3);

        String cat10 = cat.toString(FormatStyle.SIMPLE);
        assertEquals("kitty@Cat:10.3", cat10);
    }

    @Test
    public void testToStringShort() {
        String kittyStr = kitty.toString(FormatStyle.SHORT);
        System.out.println(kittyStr);

        String kateStr = kate.toString(FormatStyle.SHORT);
        System.out.println(kateStr);
    }

    @Test
    public void testToStringNormal() {
        String kittyStr = kitty.toString(FormatStyle.NORMAL);
        String expected = "kitty@Cat {\n    color = pink; \n    parent = kate@Cat; \n}";
        assertEquals(expected, kittyStr);

        String kateStr = kate.toString(FormatStyle.NORMAL);
        expected = "kate@Cat {\n    color = yellow; \n    children = (\n        kitty@Cat, \n        lily@Cat, \n    ); \n}";
        assertEquals(expected, kateStr);
    }

}
