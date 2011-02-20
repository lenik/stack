package com.bee32.plover.arch.locator;

import java.util.Map.Entry;

import javax.free.TypeHierMap;

public class ObjectLocatorRegistry {

    private TypeHierMap<ObjectLocatorSet> typemap;

    public ObjectLocatorRegistry() {
        typemap = new TypeHierMap<ObjectLocatorSet>();
    }

    public synchronized <T> void register(IObjectLocator locator) {
        Class<?> baseType = locator.getBaseType();

        ObjectLocatorSet locatorSet = (ObjectLocatorSet) typemap.get(baseType);

        if (locatorSet == null) {
            locatorSet = new ObjectLocatorSet();
            typemap.put(baseType, locatorSet);
        }
        locatorSet.add(locator);
    }

    public synchronized void unregister(IObjectLocator locator) {
        Class<?> baseType = locator.getBaseType();
        ObjectLocatorSet locatorSet = typemap.get(baseType);
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
    public <T> IObjectLocator getLocatorForObjectType(Class<?> objectType) {
        if (objectType == null)
            throw new NullPointerException("objectType");

        Entry<Class<?>, ObjectLocatorSet> floorEntry = typemap.floorEntry(objectType);

        while (floorEntry != null) {
            Class<?> baseType = floorEntry.getKey();
            ObjectLocatorSet locatorSet = (ObjectLocatorSet) floorEntry.getValue();

            if (locatorSet != null && !locatorSet.isEmpty())
                return locatorSet.iterator().next();

            floorEntry = typemap.lowerEntry(baseType);
            // Assured by Preorder.
            // assert floorEntry.getKey().isAssignableFrom(objectType);
        }
        return null;
    }

    public <T> IObjectLocator getLocatorForObject(T object) {
        if (object == null)
            throw new NullPointerException("object");

        // Local tighten.
        if (object instanceof IObjectLocator) {
            IObjectLocator locatorObj = (IObjectLocator) object;
            IObjectLocator parent = locatorObj.getParent();
            if (parent != null)
                return parent;
        }

        Class<?> objectType = object.getClass();

        Entry<Class<?>, ObjectLocatorSet> floorEntry = typemap.floorEntry(objectType);

        while (floorEntry != null) {
            Class<?> baseType = floorEntry.getKey();
            ObjectLocatorSet locatorSet = (ObjectLocatorSet) floorEntry.getValue();

            if (locatorSet != null) {
                for (IObjectLocator _locator : locatorSet) {
                    String location = _locator.getLocation(object);
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

    public LocationLookup lookup(Object obj, LocationLookup inner) {
        IObjectLocator locator = getLocatorForObject(obj);
        if (locator == null)
            if (inner == null)
                return null;
            else
                return new LocationLookup(null, obj, inner);

        String location = locator.getLocation(obj);
        assert location != null;
        LocationLookup lookup = new LocationLookup(location, obj, inner);

        return lookup(locator, lookup);
    }

    public String getLocation(Object obj) {
        LocationLookup lookup = lookup(obj, null);
        if (lookup == null)
            return null;
        else
            return lookup.joinLocation();
    }

    private static ObjectLocatorRegistry instance = new ObjectLocatorRegistry();

    public static ObjectLocatorRegistry getInstance() {
        return instance;
    }

}