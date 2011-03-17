package com.bee32.plover.orm.entity;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class EntityBeanTest
        extends Assert {

    Cat kate = new Cat("kate", "yellow");
    Cat kitty = new Cat("kitty", "pink");
    Cat lily = new Cat("lily", "white");

    public EntityBeanTest() {
        kitty.setParent(kate);
        lily.setParent(kate);

        ArrayList<Cat> children = new ArrayList<Cat>();
        children.add(kitty);
        children.add(lily);
        kate.setChildren(children);
    }

    @Test
    public void testToStringSimple() {
        Cat cat = new Cat("kitty", "pink");

        String catNoid = cat.toString(EntityFormat.SIMPLE);
        assertEquals("kitty@Cat", catNoid);

        cat.setId(10L);
        cat.setVersion(3);
        String cat10 = cat.toString(EntityFormat.SIMPLE);
        assertEquals("kitty@Cat:10.3", cat10);
    }

    @Test
    public void testToStringShort() {
        String kittyStr = kitty.toString(EntityFormat.SHORT);
        System.out.println(kittyStr);

        String kateStr = kate.toString(EntityFormat.SHORT);
        System.out.println(kateStr);
    }

    @Test
    public void testToStringNormal() {
        String kittyStr = kitty.toString(EntityFormat.NORMAL);
        String expected = "kitty@Cat {\n    color = pink; \n    parent = kate@Cat; \n}";
        assertEquals(expected, kittyStr);

        String kateStr = kate.toString(EntityFormat.NORMAL);
        expected = "kate@Cat {\n    color = yellow; \n    children = (\n        kitty@Cat, \n        lily@Cat, \n    ); \n}";
        assertEquals(expected, kateStr);
    }

}
