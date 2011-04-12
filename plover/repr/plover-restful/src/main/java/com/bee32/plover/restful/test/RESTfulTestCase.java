package com.bee32.plover.restful.test;

import java.io.IOException;

import javax.free.IllegalUsageException;

import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.test.DefaultTestSessionFactoryBean;
import com.bee32.plover.orm.context.OSIVFilter;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.DispatchServlet;
import com.bee32.plover.restful.RESTfulConfig;
import com.bee32.plover.servlet.test.WiredServletTestCase;
import com.bee32.plover.servlet.util.LazyLoadServlet;

@Import(WiredDaoTestCase.class)
public abstract class RESTfulTestCase
        extends WiredServletTestCase {

    private boolean checkAdditionalServlets;

    public String PREFIX = RESTfulConfig.preferredPrefix;

    protected RESTfulTestCase() {
        PersistenceUnit unit = UsingUtil.getUsedUnit(getClass());
        DefaultTestSessionFactoryBean.setForceUnit(unit);
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadNormalSamples();
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        // OSIV filter should before dispatch filter.
        stl.addFilter(OSIVFilter.class, "/*", 0);

        // stl.addFilter(DispatchFilter.class, "/*", 0);
        stl.addServlet(DispatchServlet.class, PREFIX + "/*");

        setupH2Console();
    }

    @Override
    protected final void configureServlets() {
        configureAdditionalServlets();

        if (!checkAdditionalServlets)
            throw new IllegalUsageException("configureAdditionalServlets is overrided.");
    }

    protected void configureAdditionalServlets() {
        checkAdditionalServlets = true;
    }

    protected void setupH2Console() {
        // addEventListener(new DbStarter());

        // context-params?

        ServletHolder servlet = stl.addServlet(LazyLoadServlet.class, "/console/*");
        servlet.setInitParameter("servlet-class", "org.h2.server.web.WebServlet");
        servlet.setInitParameter("db.url", "jdbc:h2:target/testdb");
        // servlet.setInitParameter("webAllowOthers", "");
        // servlet.setInitParameter("trace", "");
        // servlet.loadOnStartup?
    }

    public void browseAndWait(Class<?> moduleClass)
            throws IOException {
        String modulePath = OidUtil.getOid(moduleClass).toPath();
        String location = PREFIX + "/" + modulePath + "/";
        browseAndWait(location);
    }

}
