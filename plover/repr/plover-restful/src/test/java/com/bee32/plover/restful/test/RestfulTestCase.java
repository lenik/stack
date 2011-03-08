package com.bee32.plover.restful.test;

import org.h2.server.web.WebServlet;
import org.junit.runner.RunWith;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.hibernate.HibernateUnitDao;
import com.bee32.plover.test.WiredAssembledTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestfulTestCase
        extends WiredAssembledTestCase {

    protected final RestfulTesterLibrary rtl;
    protected final HibernateUnitDao hl;

    public RestfulTestCase(PersistenceUnit... units) {
        install(rtl = new RestfulTesterLibrary() {
            protected void configureServlets()
                    throws Exception {
                super.configureServlets();
                RestfulTestCase.this.configureServlets();
            }
        });
        install(hl = new HibernateUnitDao(units));
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
