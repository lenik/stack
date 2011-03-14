package com.bee32.plover.inject;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.bee32.plover.inject.spring.ContextConfigurationUtil;

public class ApplicationContextSupport
        extends AbstractXmlApplicationContext {

    private Resource[] configResources;

    public ApplicationContextSupport() {
        super();
        importAnnotatedConfigurations();
    }

    public ApplicationContextSupport(ApplicationContext parent) {
        super(parent);
        importAnnotatedConfigurations();
    }

    void importAnnotatedConfigurations() {
        Class<?> clazz = getClass();

        Collection<String> locations = ContextConfigurationUtil.getContextConfigurationLocations(clazz);

        configResources = new Resource[locations.size()];

        int i = 0;
        for (String location : locations) {
            if (location == null)
                throw new NullPointerException("location");

            Resource configResource = new ClassPathResource(location);
            configResources[i++] = configResource;
        }

        refresh();
    }

    public Resource[] getConfigResources() {
        if (configResources == null)
            throw new NullPointerException("configResources");

        return configResources;
    }

}
