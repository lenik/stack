package com.bee32.plover.arch.locator;

public interface IObjectLocator {

    IObjectLocator getParent();

    Class<?> getBaseType();

    int getPriority();

    /**
     * @param locationToken
     *            A location component.
     * @return Non-<code>null</code> object located. Returns <code>null</code> if the location isn't
     *         recognized, and the next locator for the same type should be checked.
     */
    Object locate(String locationToken);

    boolean isLocatable(Object obj);

    /**
     * Get the URI location component for the object.
     *
     * @param obj
     *            Non-<code>null</code> object.
     * @return Location string. Returns <code>null</code> if specified <code>obj</code> is not owned
     *         by this locator, and other locators for the same type should be checked.
     * @see ObjectLocatorRegistry#getLocatorForObject(Object)
     */
    String getLocation(Object obj);

}