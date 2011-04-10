package com.bee32.icsf.access.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.free.IllegalUsageException;

public class ResourceRegistry {

    static Map<String, IResourceNamespace> namespaces;
    static Map<Class<?>, IResourceNamespace> resourceTypes;

    static {
        namespaces = new HashMap<String, IResourceNamespace>();
        resourceTypes = new HashMap<Class<?>, IResourceNamespace>();

        for (IResourceNamespace rn : ServiceLoader.load(IResourceNamespace.class)) {
            String ns = rn.getNamespace();
            namespaces.put(ns, rn);

            Class<?> resourceType = rn.getResourceType();
            resourceTypes.put(resourceType, rn);
        }
    }

    static String defaultNS = null;

    /**
     * Query resource using full qualified name.
     *
     * @param fullName
     *            Qualified name in `namespace:local-name` format.
     * @return <code>null</code> If local-name is undefined with-in the namespace.
     * @throws IllegalArgumentException
     *             If namespace is undefined.
     */
    public static Resource query(String fullName) {
        String ns = defaultNS;
        String localName;

        int colon = fullName.indexOf(':');
        if (colon != -1) {
            ns = fullName.substring(0, colon);
            localName = fullName.substring(colon + 1);
        } else {
            localName = fullName;
        }

        IResourceNamespace namespace = namespaces.get(ns);
        if (namespace == null)
            throw new IllegalArgumentException("Resource namespace is undefined: " + ns);

        return namespace.getResource(localName);
    }

    /**
     * @param resource
     *            The resource object whose name would be generated.
     * @return <code>null</code> If resource is <code>null</code>.
     */
    public static String qualify(Resource resource) {
        if (resource == null)
            return null;

        Class<?> resourceType = resource.getClass();
        IResourceNamespace rn = resourceTypes.get(resourceType);
        if (rn == null)
            throw new IllegalUsageException("Namespace of " + resourceType + " is unknown");

        String ns = rn.getNamespace();
        String localName = resource.getName();
        return ns + ":" + localName;
    }

}
