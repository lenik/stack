package com.bee32.plover.xutil.m2;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class MavenPathTest
        extends Assert {

    @Test
    public void testGetClassFile() {
        File file = MavenPath.getClassFile(MavenPathTest.class);
        assertTrue(file.exists());
    }

    @Test
    public void testGetSourceFile() {
        File file = MavenPath.getSourceFile(MavenPathTest.class);
        assertTrue(file.exists());
    }

    @Test
    public void testGetResourceDir() {
        File file = MavenPath.getResourceDir(MavenPathTest.class);
        assertTrue(file.isDirectory());
    }

}
