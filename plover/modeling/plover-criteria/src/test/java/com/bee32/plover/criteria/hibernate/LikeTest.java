package com.bee32.plover.criteria.hibernate;

import org.hibernate.criterion.MatchMode;
import org.junit.Assert;
import org.junit.Test;

public class LikeTest
        extends Assert {

    static class Foo {

        String name;

        public Foo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    Foo tom = new Foo("tom");
    Foo tom2 = new Foo("Tom 2");
    Foo jerry = new Foo("Jerry");

    @Test
    public void testExact() {
        Like like = new Like(true, "name", "tom");
        assertTrue(like.filter(tom));
        assertFalse(like.filter(tom2));
        assertFalse(like.filter(jerry));
    }

    @Test
    public void testEnd() {
        Like like = new Like(true, "name", "%2");
        assertFalse(like.filter(tom));
        assertTrue(like.filter(tom2));
        assertFalse(like.filter(jerry));
    }

    @Test
    public void testStart() {
        Like like = new Like("name", "Tom", MatchMode.START);
        assertFalse(like.filter(tom));
        assertTrue(like.filter(tom2));
        assertFalse(like.filter(jerry));
    }

    @Test
    public void testAnywhere() {
        Like like = new Like("name", "%err%");
        assertFalse(like.filter(tom));
        assertFalse(like.filter(tom2));
        assertTrue(like.filter(jerry));
    }

}
