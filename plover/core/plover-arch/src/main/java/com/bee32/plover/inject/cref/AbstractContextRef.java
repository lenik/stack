package com.bee32.plover.inject.cref;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;

public abstract class AbstractContextRef
        implements IContextRef {

    @Override
    public Resource[] getConfigResources() {
        List<Resource> resourceList = new ArrayList<Resource>();
        exportResources(resourceList);

        final Resource[] configResources = resourceList.toArray(new Resource[0]);
        return configResources;
    }

    @Override
    public ApplicationContext buildApplicationContext() {
        return buildApplicationContext(null);
    }

    @Override
    public ApplicationContext buildApplicationContext(ApplicationContext parent) {

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

}
