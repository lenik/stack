package com.bee32.plover.scheduler;

import java.io.IOException;

import com.bee32.plover.servlet.test.WiredServletTestCase;

public class HelloServer
        extends WiredServletTestCase {

    @Override
    public void mainLoop()
            throws IOException {
        new HelloServer().browseAndWait();
    }

}
