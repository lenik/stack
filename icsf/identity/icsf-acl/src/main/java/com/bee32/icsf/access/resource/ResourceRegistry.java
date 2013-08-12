package com.bee32.icsf.access.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import javax.free.IllegalUsageException;

/**
 * 资源注册表
 */
public class ResourceRegistry {

    static Set<IResourceNamespace> namespaces;
    static Map<String, IResourceNamespace> namespaceMap;
    static Map<Class<?>, IResourceNamespace> resourceTypes;

    static {
        namespaces = new HashSet<IResourceNamespace>();
        namespaceMap = new HashMap<String, IResourceNamespace>();
        resourceTypes = new HashMap<Class<?>, IResourceNamespace>();

        for (IResourceNamespace rn : ServiceLoader.load(IResourceNamespace.class)) {
            namespaces.add(rn);

            String ns = rn.getNamespace();
            namespaceMap.put(ns, rn);

            Class<?> resourceType = rn.getResourceType();
            resourceTypes.put(resourceType, rn);
        }
    }

    static String defaultNS = null;

    /**
     * Query resource using full qualified name.
     *
     * @param qualifiedName
     *            Qualified name in `namespace:local-name.` format.
     * @return <code>null</code> If local-name is undefined with-in the namespace.
     * @throws IllegalArgumentException
     *             If namespace is undefined.
     */
    public static Resource query(String qualifiedName) {
        if (qualifiedName == null)
            throw new NullPointerException("qualifiedName");

        String ns = defaultNS;
        String localName;
        int colon = qualifiedName.indexOf(':');
        if (colon != -1) {
            ns = qualifiedName.substring(0, colon);
            localName = qualifiedName.substring(colon + 1);
        } else {
            localName = qualifiedName;
        }

        if (localName.endsWith("."))
            localName = localName.substring(0, localName.length() - 1);

        IResourceNamespace namespace = namespaceMap.get(ns);
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
        if (localName.endsWith(":"))
            return ns + ":" + localName;
        else
            return ns + ":" + localName + ".";
    }

    public static IResourceNamespace getNamespace(String ns) {
        return namespaceMap.get(ns);
    }

    public static Collection<IResourceNamespace> getNamespaces() {
        return Collections.unmodifiableSet(namespaces);
    }

}
