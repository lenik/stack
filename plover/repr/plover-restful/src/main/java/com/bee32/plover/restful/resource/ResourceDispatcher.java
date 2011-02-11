package com.bee32.plover.restful.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.restful.DispatchFilter;
import com.bee32.plover.restful.ModuleManager;

public class ResourceDispatcher
        extends AbstractDispatcher {

    private final Map<String, ResourceFactory> resourceTypes;

    public ResourceDispatcher() {
        this.resourceTypes = new HashMap<String, ResourceFactory>();
    }

    public void register(String type, ResourceFactory factory) {
        if (type == null)
            throw new NullPointerException("type");

        if (factory == null)
            throw new NullPointerException("factory");

        resourceTypes.put(type, factory);
    }

    /**
     * The initial/root context is: {@link ModuleManager}.
     *
     * @see DispatchFilter#getRootContext()
     * @see ModuleManager
     */
    @Override
    public IResource dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        String type = tokens.peek();
        ResourceFactory resourceFactory = resourceTypes.get(type);
        if (resourceFactory == null)
            return null;

        tokens.shift();

        String path = tokens.getRemainingPath();
        IResource resource = resourceFactory.resolve(path);

        if (resource == null)
            throw new DispatchException();

        tokens.skip(tokens.available());
        return resource;
    }

    private static final ResourceDispatcher instance;
    static {
        instance = new ResourceDispatcher();

        // Load services.
        for (ResourceFactory factory : ServiceLoader.load(ResourceFactory.class)) {
            String type = factory.getType();
            instance.register(type, factory);
        }
    }

    public static ResourceDispatcher getInstance() {
        return instance;
    }

}
