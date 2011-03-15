package com.bee32.plover.restful.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.h2.server.web.WebServlet;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;

import com.bee32.plover.inject.spring.ContextConfigurationUtil;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.test.AssembledTestCase;

public abstract class RestfulTestCase
        extends AssembledTestCase {

    protected RestfulTesterLibrary rtl;

    public RestfulTestCase() {
        // super(units);
        initialize();
    }

    // @Override
    // public void afterPropertiesSet() {
    // super.afterPropertiesSet();
    // }

    void initialize() {
        install(rtl = new LocalRTL());
    }

    class LocalRTL
            extends RestfulTesterLibrary {

        @Override
        protected void configureServlets()
                throws Exception {
            super.configureServlets();

            RestfulTestCase.this.configureBuiltinServlets();
            RestfulTestCase.this.configureServlets();
        }

    }

    void configureBuiltinServlets() {
        // setup
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener();

        rtl.addEventListener(contextLoaderListener);

        Collection<String> baseLocations //
        = ContextConfigurationUtil.getContextConfigurationLocations(WiredDaoTestCase.class);

        Collection<String> userLocations //
        = ContextConfigurationUtil.getContextConfigurationLocations(getClass());

        Set<String> mergedLocations = new LinkedHashSet<String>();
        mergedLocations.addAll(baseLocations);
        mergedLocations.addAll(userLocations);

        StringBuilder locations = new StringBuilder();
        for (String location : mergedLocations) {
            String respath = "classpath:" + location;

            if (locations.length() != 0)
                locations.append(", ");

            locations.append(respath);
        }

        Map<String, String> contextParams = new HashMap<String, String>();
        contextParams.put("contextConfigLocation", locations.toString());

        rtl.getContext().setInitParams(contextParams);

        setupH2Console();
    }

    protected void configureServlets() {
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
