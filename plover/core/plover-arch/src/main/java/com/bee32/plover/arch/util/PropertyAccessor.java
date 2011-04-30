package com.bee32.plover.arch.util;

/**
 * @param S
 *            Type of the owner object.
 * @param T
 *            Type of the property.
 */
public abstract class PropertyAccessor<S, T>
        implements IPropertyAccessor<S, T> {

    private final Class<T> propertyType;

    @SuppressWarnings("unchecked")
    public PropertyAccessor(Class<?> propertyType) {
        if (propertyType == null)
            throw new NullPointerException("propertyType");
        this.propertyType = (Class<T>) propertyType;
    }

    @Override
    public Class<T> getType() {
        return propertyType;
    }

}
