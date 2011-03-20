package com.bee32.plover.web.faces.test;

import java.io.IOException;

import org.junit.Test;

public class FaceletsTestCaseTest
        extends FaceletsTestCase {

    @Test
    public void play()
            throws IOException {
        browseAndWait("/guess.jsf");
    }

}
