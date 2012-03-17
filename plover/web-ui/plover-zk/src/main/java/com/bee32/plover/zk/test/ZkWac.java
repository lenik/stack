package com.bee32.plover.zk.test;

import org.eclipse.jetty.servlet.ServletHolder;
import org.zkoss.web.servlet.dsp.InterpreterServlet;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class ZkWac
        extends AbstractWac {
    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        /**
         * This is a session-listener, not a context-listener, so won't go into *Scl.
         *
         * @see javax.servlet.http.HttpSessionListener
         */
        HttpSessionListener httpSessionListener = new HttpSessionListener();
        stl.addEventListener(httpSessionListener);

        ServletHolder zulServlet = stl.addServlet(DHtmlLayoutServlet.class, "*.zul");
        zulServlet.setInitParameter("update-uri", "/zkau");
        zulServlet.setInitParameter("log-level", "DEBUG");

        stl.addServlet(DHtmlUpdateServlet.class, "/zkau/*");
        stl.addServlet(InterpreterServlet.class, "*.dsp");
    }

}
