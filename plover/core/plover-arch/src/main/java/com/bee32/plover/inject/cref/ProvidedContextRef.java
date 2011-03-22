package com.bee32.plover.inject.cref;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.core.io.Resource;

public class ProvidedContextRef
        extends AbstractContextRef {

    private Collection<Resource> resources;

    private ProvidedContextRef(Resource... resources) {
        this.resources = Arrays.asList(resources);
    }

    private ProvidedContextRef(Collection<Resource> resources) {
        if (resources == null)
            throw new NullPointerException("resources");
        this.resources = resources;
    }

    @Override
    public Resource[] getConfigResources() {
        return resources.toArray(new Resource[0]);
    }

    @Override
    public void exportResources(List<Resource> resources) {
        resources.addAll(resources);
    }

}
