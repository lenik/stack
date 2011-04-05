package com.bee32.plover.restful.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.Arrival;
import com.bee32.plover.disp.util.IArrival;
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
     * The root context should be module manager.
     *
     * @param arrival
     *            Not used actually, because this resource dispatcher manages children resources by
     *            itself.
     * @see DispatchFilter#getRootContext()
     * @see ModuleManager
     */
    @Override
    public IArrival dispatch(IArrival arrival, ITokenQueue tokens)
            throws DispatchException {
        // The context object is ignored.
        // Object obj = context.getObject();

        String type = tokens.peek();
        ResourceFactory resourceFactory = resourceTypes.get(type);
        if (resourceFactory == null)
            return null;

        tokens.shift();

        String path = tokens.getRemainingPath();
        IResource resource;
        try {
            resource = resourceFactory.resolve(path);
        } catch (ResourceResolveException e) {
            throw new DispatchException(e.getMessage(), e);
        }

        if (resource == null)
            throw new DispatchException("Resource is resolved to null");

        int available = tokens.available();
        String[] consumedTokens = new String[1 + available];
        consumedTokens[0] = type;
        for (int i = 1; i <= available; i++) {
            consumedTokens[i] = tokens.shift();
        }

        return new Arrival(arrival, resource, consumedTokens);
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
