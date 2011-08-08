package com.bee32.sem.inventory;

import java.io.IOException;

import com.bee32.plover.web.faces.test.FaceletsTestCase;

public class CtrlTest
        extends FaceletsTestCase {
    @Override
    protected void configureContext() {
        super.configureContext();

        // javax.faces.PROJECT_STAGE
        stl.getServletContext().setAttribute("javax.faces.PROJECT_STAGE", "Development");
        stl.setAttribute("javax.faces.PROJECT_STAGE", "Development");

        stl.getServletContext().addInitParam("javax.faces.PROJECT_STAGE", "Development");
    }

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws IOException {
        new CtrlTest().browseAndWait("/login.xhtml");
    }
}
