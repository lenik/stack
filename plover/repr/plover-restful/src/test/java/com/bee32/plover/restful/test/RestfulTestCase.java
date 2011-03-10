package com.bee32.plover.restful.test;

import org.h2.server.web.WebServlet;
import org.mortbay.jetty.servlet.ServletHolder;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.WiredDaoTestCase;

public abstract class RestfulTestCase
        extends WiredDaoTestCase {

    protected RestfulTesterLibrary rtl;

    public RestfulTestCase(PersistenceUnit... units) {
        super(units);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        install(rtl = new RestfulTesterLibrary() {
            protected void configureServlets()
                    throws Exception {
                super.configureServlets();
                RestfulTestCase.this.configureServlets();
            }
        });
    }

    protected void configureServlets() {
        setupH2Console();
    }

    protected void setupH2Console() {
        // addEventListener(new DbStarter());

        // context-params?

        ServletHolder servlet = rtl.addServlet(WebServlet.class, "/console/*");
        servlet.setInitParameter("db.url", "jdbc:h2:target/testdb");
        // servlet.setInitParameter("webAllowOthers", "");
        // servlet.setInitParameter("trace", "");
        // servlet.loadOnStartup?
    }

}
