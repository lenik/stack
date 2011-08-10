package com.bee32.plover.scheduler;

import java.io.IOException;

import com.bee32.plover.servlet.test.WiredServletTestCase;

public class HelloServer
        extends WiredServletTestCase {

    public static void main(String[] args)
            throws IOException {
        new HelloServer().browseAndWait();
    }

}
