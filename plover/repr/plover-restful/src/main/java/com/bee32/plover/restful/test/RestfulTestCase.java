package com.bee32.plover.restful.test;

import javax.free.IllegalUsageException;

import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.orm.config.test.DefaultTestSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.restful.context.SimpleApplicationContextUtil;
import com.bee32.plover.servlet.test.WiredServletTestCase;
import com.bee32.plover.servlet.util.LazyLoadServlet;

public abstract class RestfulTestCase
        extends WiredServletTestCase {

    private ApplicationContext applicationContext;

    private boolean checkAdditionalServlets;

    protected RestfulTestCase() {
        this(WiredDaoTestCase.class, (ApplicationContext) null);
    }

    protected RestfulTestCase(Class<?> configClass) {
        this(configClass, null);
    }

    protected RestfulTestCase(ApplicationContext applicationContext) {
        this(WiredDaoTestCase.class, applicationContext);
    }

    protected RestfulTestCase(Class<?> configClass, ApplicationContext applicationContext) {
        super(configClass);
        this.applicationContext = applicationContext;

        PersistenceUnit unit = UsingUtil.getUsedUnit(getClass());
        DefaultTestSessionFactoryBean.setForceUnit(unit);
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadNormalSamples();
    }

    @Override
    protected void configureContext() {
        super.configureContext();

        if (applicationContext != null) {
            String key = SimpleApplicationContextUtil.rootApplicationContextKey;
            stl.setAttribute(key, applicationContext);
        }

        // String wacAttr=WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        stl.addFilter(DispatchFilter.class, "/*", 0);

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

}
