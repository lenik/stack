package com.bee32.plover.zk.test;

import java.io.IOException;

import org.junit.Test;

public class ZkTestCaseTest
        extends ZkTestCase {

    @Test
    public void test() {
    }

    public static void main(String[] args)
            throws IOException, Exception {
        new ZkTestCaseTest().browseAndWait(//
                // "/zhello.jsf" //
                "/zhello.zul" //
                );
    }

}
