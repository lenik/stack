package com.bee32.plover.arch.naming;

import java.util.Map.Entry;

import javax.free.TypeHierMap;

public class ReverseLookupRegistry {

    private TypeHierMap<NamedNodeSet> typemap;

    public ReverseLookupRegistry() {
        typemap = new TypeHierMap<NamedNodeSet>();
    }

    public synchronized <T> void register(INamedNode locator) {
        Class<?> baseType = locator.getChildType();

        NamedNodeSet locatorSet = (NamedNodeSet) typemap.get(baseType);

        if (locatorSet == null) {
            locatorSet = new NamedNodeSet();
            typemap.put(baseType, locatorSet);
        }
        locatorSet.add(locator);
    }

    public synchronized void unregister(INamedNode locator) {
        Class<?> baseType = locator.getChildType();
        NamedNodeSet locatorSet = typemap.get(baseType);
        if (locatorSet != null) {
            locatorSet.remove(locator);
            if (locatorSet.isEmpty())
                typemap.remove(baseType);
        }
    }

    /**
     * Get (any) object locator for the corresponding object type.
     *
     * @return The locator with highest priority for a specific object type is returned. Return
     *         <code>null</code> if none available.
     */
    public <T> INamedNode getLocatorForObjectType(Class<?> objectType) {
        if (objectType == null)
            throw new NullPointerException("objectType");

        Entry<Class<?>, NamedNodeSet> floorEntry = typemap.floorEntry(objectType);

        while (floorEntry != null) {
            Class<?> baseType = floorEntry.getKey();
            NamedNodeSet locatorSet = (NamedNodeSet) floorEntry.getValue();

            if (locatorSet != null && !locatorSet.isEmpty())
                return locatorSet.iterator().next();

            floorEntry = typemap.lowerEntry(baseType);
            // Assured by Preorder.
            // assert floorEntry.getKey().isAssignableFrom(objectType);
        }
        return null;
    }

    public <T> INamedNode getLocatorForObject(T object) {
        if (object == null)
            throw new NullPointerException("object");

        // Local tighten.
        if (object instanceof INamedNode) {
            INamedNode locatorObj = (INamedNode) object;
            INamedNode parent = locatorObj.getParent();
            if (parent != null)
                return parent;
        }

        Class<?> objectType = object.getClass();

        Entry<Class<?>, NamedNodeSet> floorEntry = typemap.floorEntry(objectType);

        while (floorEntry != null) {
            Class<?> baseType = floorEntry.getKey();
            NamedNodeSet locatorSet = (NamedNodeSet) floorEntry.getValue();

            if (locatorSet != null) {
                for (INamedNode _locator : locatorSet) {
                    String location = _locator.getChildName(object);
                    if (location != null)
                        return _locator;
                }
            }

            floorEntry = typemap.lowerEntry(baseType);
            // Assured by Preorder.
            // assert floorEntry.getKey().isAssignableFrom(objectType);
        }
        return null;
    }

    public LookupChain lookup(Object obj, LookupChain inner) {
        INamedNode locator = getLocatorForObject(obj);
        if (locator == null)
            if (inner == null)
                return null;
            else
                return new LookupChain(null, obj, inner);

        String location = locator.getChildName(obj);
        assert location != null;
        LookupChain lookup = new LookupChain(location, obj, inner);

        return lookup(locator, lookup);
    }

    public String getLocation(Object obj) {
        LookupChain chain = lookup(obj, null);
        if (chain == null)
            return null;
        else
            return chain.join();
    }

    private static ReverseLookupRegistry instance = new ReverseLookupRegistry();

    public static ReverseLookupRegistry getInstance() {
        return instance;
    }

}