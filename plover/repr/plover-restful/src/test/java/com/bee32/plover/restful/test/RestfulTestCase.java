package com.bee32.plover.restful.test;

import org.h2.server.web.WebServlet;
import org.mortbay.jetty.servlet.ServletHolder;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ModuleLoader;
import com.bee32.plover.orm.entity.HibernateEntityRepository;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.hibernate.HibernateLibrary;
import com.bee32.plover.test.AssembledTestCase;

public class RestfulTestCase
        extends AssembledTestCase {

    protected final RestfulTesterLibrary rtl;
    protected final HibernateLibrary hl;

    public RestfulTestCase(PersistenceUnit... units) {
        install(rtl = new RestfulTesterLibrary() {
            protected void configureServlets()
                    throws Exception {
                super.configureServlets();
                RestfulTestCase.this.configureServlets();
            }
        });
        install(hl = new HibernateLibrary(units));

        injectER();
    }

    void injectER() {
        // SimpleBooks.init(hl.getSessionFactory());
        for (IModule module : ModuleLoader.getModules()) {
            for (Object managed : module.getChildren()) {
                if (managed instanceof HibernateEntityRepository<?, ?>) {
                    HibernateEntityRepository<?, ?> er = (HibernateEntityRepository<?, ?>) managed;
                    er.setSessionFactory(hl.getSessionFactory());
                }
            }
        }
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
