package com.bee32.plover.restful.test;

import org.mortbay.jetty.Handler;

import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.servlet.test.ServletTestCase;

public class DispServiceTestCase
        extends ServletTestCase {

    @Override
    protected void setup()
            throws Exception {
        addFilter(DispatchFilter.class, "/", Handler.DEFAULT);
    }

}
