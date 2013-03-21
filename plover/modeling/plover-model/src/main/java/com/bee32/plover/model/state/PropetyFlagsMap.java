package com.bee32.plover.model.state;

import java.util.HashMap;

public class PropetyFlagsMap
        extends HashMap<String, PropertyFlags> {

    private static final long serialVersionUID = 1L;

    @Override
    public PropertyFlags get(Object key) {
        PropertyFlags flags = super.get(key);
        if (flags == null)
            return DefaultPropertyFlags.getInstance();
        return flags;
    }

    public PropertyFlags resolve(String property) {
        PropertyFlags flags = super.get(property);
        if (flags == null) {
            flags = new PropertyFlags();
            put(property, flags);
        }
        return flags;
    }

    public void setBoldForAllProperties(boolean value) {
        for (PropertyFlags flags : values())
            flags.setBold(value);
    }

    public void setLockedForAllProperties(boolean value) {
        for (PropertyFlags flags : values())
            flags.setLocked(value);
    }

    public void setVisibleForAllProperties(boolean value) {
        for (PropertyFlags flags : values())
            flags.setVisible(value);
    }

    public void setBoldProperties(String... propertyNames) {
        for (String propertyName : propertyNames) {
            PropertyFlags flags = resolve(propertyName);
            flags.setBold(true);
        }
    }

    public void setLockedProperties(String... propertyNames) {
        for (String propertyName : propertyNames) {
            PropertyFlags flags = resolve(propertyName);
            flags.setLocked(true);
        }
    }

    public void setInvisibleProperties(String... propertyNames) {
        for (String propertyName : propertyNames) {
            PropertyFlags flags = resolve(propertyName);
            flags.setVisible(false);
        }
    }

}
