package com.bee32.icsf.access.resource;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @see ResourceRegistry
 */
@Lazy
@Component
public class ScannedResourceRegistry
        implements InitializingBean {

    @Inject
    AccessPointManager accessPointManager;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        accessPointManager.scan();
    }

    public Resource query(String fullName) {
        return ResourceRegistry.query(fullName);
    }

    public String toName(Resource resource) {
        return ResourceRegistry.toName(resource);
    }

}
