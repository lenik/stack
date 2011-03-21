package com.bee32.plover.servlet.test;

import java.util.HashMap;
import java.util.Map;

import org.mortbay.jetty.servlet.Context;

public class RabbitServletContext
        extends OverlappedContext {

    public RabbitServletContext() {
        super(Context.SESSIONS | Context.SECURITY);
    }

    public synchronized void addInitParam(String name, String value) {
        Map<String, String> initParams = getInitParams();
        if (initParams == null) {
            initParams = new HashMap<String, String>();
            setInitParams(initParams);
        }
        initParams.put(name, value);
    }

}
