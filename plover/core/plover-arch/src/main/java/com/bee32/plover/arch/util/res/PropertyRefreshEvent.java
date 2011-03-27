package com.bee32.plover.arch.util.res;

import java.util.EventObject;

public class PropertyRefreshEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    private String propertyName;

    /**
     * Refresh all properties.
     */
    public PropertyRefreshEvent(Object source) {
        super(source);
    }

    /**
     * Refresh a particular property.
     */
    public PropertyRefreshEvent(Object source, String propertyName) {
        super(source);
        this.propertyName = propertyName;
    }

    /**
     * The name of property which should be refreshed.
     *
     * @return <code>null</code> means all properties.
     */
    public String getPropertyName() {
        return propertyName;
    }

    public boolean isRefreshAll() {
        return propertyName == null;
    }

    @Override
    public String toString() {
        if (propertyName == null)
            return "*";
        return propertyName;
    }

}
