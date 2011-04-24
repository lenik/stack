package com.bee32.plover.arch.util;

/**
 * @param S
 *            Type of the owner object.
 * @param T
 *            Type of the property.
 */
public abstract class PropertyAccessor<S, T> {

    private final Class<T> propertyType;

    public PropertyAccessor(Class<T> propertyType) {
        if (propertyType == null)
            throw new NullPointerException("propertyType");
        this.propertyType = propertyType;
    }

    public Class<T> getType() {
        return propertyType;
    }

    public abstract T get(S obj);

    public abstract void set(S obj, T value);

}
