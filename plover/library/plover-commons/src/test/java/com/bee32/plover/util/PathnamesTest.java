package com.bee32.plover.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PathnamesTest {

    @Test
    public void testSimple() {
        String actual = Pathnames.joinPath("a/b/c", "d");
        assertEquals("a/b/d", actual);

        actual = Pathnames.joinPath("a/b/c", "./d");
        assertEquals("a/b/d", actual);

        actual = Pathnames.joinPath("a/b/c", "../d");
        assertEquals("a/d", actual);

        actual = Pathnames.joinPath("a/b/c", "");
        assertEquals("a/b/", actual);
    }

    @Test
    public void testOutOfBound() {
        String actual = Pathnames.joinPath("a/b/c", "/");
        assertEquals("/", actual);

        actual = Pathnames.joinPath("a/b/c", "/a/b/c");
        assertEquals("/a/b/c", actual);

        actual = Pathnames.joinPath("/", "a");
        assertEquals("/a", actual);

        actual = Pathnames.joinPath("a/", "a");
        assertEquals("a/a", actual);

        actual = Pathnames.joinPath("a/", "..");
        // a/ + ../.
        // a + .
        // ./a + .
        // . + ""
        // ./
        assertEquals("./", actual);

        actual = Pathnames.joinPath("a/", "../..");
        // a/ + ../..
        // a + ..
        // a + ../.
        // .. + .
        // .. + ""
        // ../
        assertEquals("../", actual);
    }

}
