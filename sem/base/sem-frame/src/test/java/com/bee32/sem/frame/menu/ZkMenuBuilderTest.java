package com.bee32.sem.frame.menu;

import java.io.IOException;

import org.junit.Test;

import com.bee32.plover.zk.test.ZkTestCase;

public class ZkMenuBuilderTest
        extends ZkTestCase {

    @Test
    public void test() {
    }

    public static void main(String[] args)
            throws IOException, Exception {
        new ZkMenuBuilderTest().browseAndWait(//
                "main-menu.zul");
    }

}
