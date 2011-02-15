package com.bee32.plover.test;

import org.mortbay.jetty.Handler;

import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.test.servlet.ServletTestCase;

public class RestfulServiceTestCase
        extends ServletTestCase {

    @Override
    protected void setup()
            throws Exception {
        super.setup();
        addFilter(DispatchFilter.class, "/", Handler.DEFAULT);
    }

}
