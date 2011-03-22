package com.bee32.plover.servlet.test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.bee32.plover.inject.cref.ContextRef;
import com.bee32.plover.inject.spring.ContextConfigurationUtil;
import com.bee32.plover.test.JUnitHelper;
import com.bee32.plover.test.WiredTestCase;

public abstract class WiredServletTestCase
        extends ServletTestCase {

    private final Class<?> altBaseClass;

    protected WiredServletTestCase() {
        this(WiredTestCase.class);
    }

    protected WiredServletTestCase(Class<?> altBaseClass) {
        this.altBaseClass = altBaseClass;
    }

    @Override
    protected void configureContext() {
        super.configureContext();

        // setup
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener();

        stl.addEventListener(contextLoaderListener);

        Set<String> mergedLocations = new LinkedHashSet<String>();

        if (altBaseClass != null) {
            Collection<String> baseLocations = ContextConfigurationUtil.getContextConfigurationLocations(altBaseClass);
            mergedLocations.addAll(baseLocations);
        }

        Collection<String> userLocations = ContextConfigurationUtil.getContextConfigurationLocations(getClass());
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

    protected WiredServletTestCase wire()
            throws Exception {
        return wire(true);
    }

    protected WiredServletTestCase wire(boolean dropThis)
            throws Exception {
        if (!dropThis)
            throw new UnsupportedOperationException("Sorry, it's unsupported to reuse this object.");

        WiredServletTestCase wrapped = JUnitHelper.createWrappedInstance(getClass());

        ApplicationContext context = new ContextRef(getClass()).buildApplicationContext();
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBean(wrapped);

        return wrapped;
    }

}
