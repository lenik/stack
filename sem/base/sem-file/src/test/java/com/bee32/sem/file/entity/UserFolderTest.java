package com.bee32.sem.file.entity;

import org.junit.Assert;
import org.junit.Test;

public class UserFolderTest
        extends Assert {

    @Test
    public void testGetPath() {
        UserFolder a = new UserFolder(null, "a");
        assertEquals(a.getPath(), "a");

        UserFolder b = new UserFolder(a, "book");
        assertEquals(b.getPath(), "a/book");

        UserFolder c = new UserFolder(b, "cat");
        assertEquals(c.getPath(), "a/book/cat");
    }

}
