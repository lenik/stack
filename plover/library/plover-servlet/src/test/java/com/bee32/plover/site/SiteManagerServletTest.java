package com.bee32.plover.site;

import java.io.IOException;

import com.bee32.plover.servlet.PloverServletModule;
import com.bee32.plover.servlet.test.ServletTestCase;

public class SiteManagerServletTest
        extends ServletTestCase {

    @Override
    protected void configureServlets() {
        new SiteManagerWac().configureServlets(stl);
    }

    public static void main(String[] args)
            throws IOException {
        new SiteManagerServletTest().browseAndWait(PloverServletModule.PREFIX + "/index");
    }

}
