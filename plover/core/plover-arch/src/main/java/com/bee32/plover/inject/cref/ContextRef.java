package com.bee32.plover.inject.cref;

import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.bee32.plover.inject.spring.ContextConfigurationUtil;

public class ContextRef
        extends AbstractContextRef {

    private IContextRef[] parents;
    private Class<?> searchClass;

    private transient ApplicationContext applicationContext;

    public ContextRef() {
        this((Class<?>) null);
    }

    public ContextRef(IContextRef... parents) {
        this(null, parents);
    }

    public ContextRef(Class<?> searchClass, IContextRef... parents) {
        if (searchClass == null)
            searchClass = getClass();
        this.searchClass = searchClass;

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

    @Override
    public void exportResources(List<Resource> resources) {
        if (parents != null) {
            for (IContextRef parent : parents)
                parent.exportResources(resources);
        }

        Collection<String> locations = ContextConfigurationUtil.getContextConfigurationLocations(searchClass);

        for (String location : locations) {
            if (location == null)
                throw new NullPointerException("location");

            Resource configResource = new ClassPathResource(location);
            resources.add(configResource);
        }
    }

}
