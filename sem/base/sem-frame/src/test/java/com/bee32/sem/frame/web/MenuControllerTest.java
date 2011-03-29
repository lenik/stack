package com.bee32.sem.frame.web;

import java.io.IOException;

import org.junit.Test;

import com.bee32.plover.zk.test.ZkTestCase;

public class MenuControllerTest
        extends ZkTestCase {

    @Test
    public void test() {

    }

    public static void main(String[] args)
            throws IOException, Exception {
        new MenuControllerTest().wire().browseAndWait();
    }

}
