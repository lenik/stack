package com.bee32.plover.servlet.test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bee32.plover.inject.spring.ContextConfigurationUtil;
import com.bee32.plover.test.WiredTestCase;

public abstract class WiredServletTestCase
        extends ServletTestCase {

    private final Class<?> configClass;

    protected WiredServletTestCase() {
        this(WiredTestCase.class);
    }

    protected WiredServletTestCase(Class<?> configClass) {
        if (configClass == null)
            throw new NullPointerException("configClass");
        this.configClass = configClass;
    }

    @Override
    protected void configureContext() {
        super.configureContext();

        // setup
        ContextLoaderListener contextLoaderListener = new RootContextLoaderListener() {

            @Override
            public void contextInitialized(ServletContextEvent event) {
                super.contextInitialized(event);

                ServletContext sc = event.getServletContext();
                ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
                applicationInitialized(applicationContext);
            }

            @Override
            public void contextDestroyed(ServletContextEvent event) {
                ServletContext sc = event.getServletContext();
                ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
                applicationDestroyed(applicationContext);

                super.contextDestroyed(event);
            }

        };

        stl.addEventListener(contextLoaderListener);

        Set<String> mergedLocations = new LinkedHashSet<String>();

        if (configClass != null) {
            Collection<String> baseLocations = ContextConfigurationUtil.getContextConfigurationLocations(configClass);
            mergedLocations.addAll(baseLocations);
        }

        Class<?> configClass = getClass();
        Collection<String> userLocations = ContextConfigurationUtil.getContextConfigurationLocations(configClass);
        mergedLocations.addAll(userLocations);

        StringBuilder locations = new StringBuilder();
        for (String location : mergedLocations) {
            String respath = "classpath:" + location;

            if (locations.length() != 0)
                locations.append(", ");

            locations.append(respath);
        }

        RabbitServletContext context = stl.getServletContext();
        context.addInitParam("contextConfigLocation", locations.toString());
    }

    protected void applicationInitialized(ApplicationContext applicationContext) {
    }

    protected void applicationDestroyed(ApplicationContext applicationContext) {
    }

}
