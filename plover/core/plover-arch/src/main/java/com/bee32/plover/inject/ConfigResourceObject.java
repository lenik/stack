package com.bee32.plover.inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.bee32.plover.inject.spring.ContextConfigurationUtil;

public class ConfigResourceObject {

    private ConfigResourceObject[] parents;

    private transient ApplicationContext applicationContext;

    public ConfigResourceObject() {
        this.parents = null;
    }

    public ConfigResourceObject(ConfigResourceObject... parents) {
        this.parents = parents;
    }

    public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            synchronized (this) {
                if (applicationContext == null) {
                    applicationContext = buildApplicationContext();
                }
            }
        }
        return applicationContext;
    }

    protected ApplicationContext buildApplicationContext() {
        return buildApplicationContext(null);
    }

    protected ApplicationContext buildApplicationContext(ApplicationContext parent) {

        final Resource[] configResources = getConfigResources();

        AbstractXmlApplicationContext applicationContext = new AbstractXmlApplicationContext(parent) {

            @Override
            protected Resource[] getConfigResources() {
                return configResources;
            }

        };

        applicationContext.refresh();

        return applicationContext;
    }

    public Resource[] getConfigResources() {
        List<Resource> resourceList = new ArrayList<Resource>();
        exportResources(resourceList);

        final Resource[] configResources = resourceList.toArray(new Resource[0]);
        return configResources;
    }

    protected void exportResources(List<Resource> resources) {
        if (parents != null) {
            for (ConfigResourceObject parent : parents)
                parent.exportResources(resources);
        }

        Class<?> clazz = getClass();
        Collection<String> locations = ContextConfigurationUtil.getContextConfigurationLocations(clazz);

        for (String location : locations) {
            if (location == null)
                throw new NullPointerException("location");

            Resource configResource = new ClassPathResource(location);
            resources.add(configResource);
        }
    }

}
