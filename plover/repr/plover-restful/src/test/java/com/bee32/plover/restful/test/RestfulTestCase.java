package com.bee32.plover.restful.test;

import javax.inject.Inject;

import org.h2.server.web.WebServlet;
import org.mortbay.jetty.servlet.ServletHolder;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.hibernate.HibernateConfigurer;
import com.bee32.plover.orm.util.hibernate.HibernateUnitConfigurer;
import com.bee32.plover.test.WiredAssembledTestCase;

public abstract class RestfulTestCase
        extends WiredAssembledTestCase {

    @Inject
    private HibernateConfigurer hibernateConfigurer;

    protected RestfulTesterLibrary rtl;
    protected HibernateUnitConfigurer hl;

    private PersistenceUnit[] units;

    public RestfulTestCase(PersistenceUnit... units) {
        this.units = units;
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
        install(hl = new HibernateUnitConfigurer(hibernateConfigurer, units));
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
