package com.bee32.plover.servlet.test;

import java.io.IOException;

import org.junit.Test;

public class ServletTestCaseTest
        extends ServletTestCase {

    @Override
    protected void configureServlets() {
    }

    @Test
    public void browse()
            throws IOException {
        super.browseAndWait();
    }

}
