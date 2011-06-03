package com.bee32.plover.servlet.mvc;

import java.io.IOException;

import com.bee32.plover.servlet.test.WiredServletTestCase;

public class CompositeControllerTest
        extends WiredServletTestCase {

    @Override
    protected void configureServlets() {
    }

    public static void main(String[] args)
            throws IOException {
        new CompositeControllerTest().browseAndWait("/my/sub/test1" + SpringMVCConfig.suffix);
    }

}
