package com.bee32.plover.arch.util.res;

public interface IPropertyDispatcherAware {

    /**
     * This happens before the dispatcher is usable.
     *
     * So you should not call {@link IPropertyDispatcher#dispatch(String, String)} at this time.
     *
     * @param propertyDispatcher
     *            The property dispatcher which WILL dispatch properties to this instance.
     */
    void setPropertyDispatcher(IPropertyDispatcher propertyDispatcher);

}
