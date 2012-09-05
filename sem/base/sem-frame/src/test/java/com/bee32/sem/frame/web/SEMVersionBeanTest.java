package com.bee32.sem.frame.web;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class SEMVersionBeanTest
        extends Assert {

    @Test
    public void testTwo() {
        SEMVersionBean version = new SEMVersionBean("1.1 234");
        assertEquals("dev", version.getTag());
        assertEquals("234", version.getRelease());
        assertEquals("1.1", version.getVersion());
    }

    // @IntergratedTest
    @Test
    public void testReal() {
        SEMVersionBean bean = new SEMVersionBean();
        for (VersionDescription ver : bean.getVersionLog100()) {
            System.out.println(ver.getVersion() + ": [" + ver.getDate() + "] " + Arrays.asList(ver.getAuthors()));
            for (String commit : ver.getCommits())
                System.out.println("    > " + commit);
            System.out.println();
        }
    }

}
