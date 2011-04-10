package com.bee32.plover.zk.test;

import org.mortbay.jetty.servlet.ServletHolder;
import org.zkoss.web.servlet.dsp.InterpreterServlet;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

import com.bee32.plover.web.faces.test.FaceletsTestCase;

public class ZkTestCase
        extends FaceletsTestCase {

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        HttpSessionListener httpSessionListener = new HttpSessionListener();
        stl.addEventListener(httpSessionListener);

        ServletHolder zulServlet = stl.addServlet(DHtmlLayoutServlet.class, "*.zul");
        zulServlet.setInitParameter("update-uri", "/zkau");
        zulServlet.setInitParameter("log-level", "DEBUG");

        stl.addServlet(DHtmlUpdateServlet.class, "/zkau/*");
        stl.addServlet(InterpreterServlet.class, "*.dsp");
    }

}
