package com.bee32.sem.world.monetary;

import java.io.IOException;

import com.bee32.plover.servlet.test.WiredServletTestCase;

public class FxrCommitJobTest
        // extends QuartzPlayer
        extends WiredServletTestCase {

    public static void main(String[] args)
            throws IOException {
        new FxrCommitJobTest().browseAndWait("/quartz/stat");
    }

}
