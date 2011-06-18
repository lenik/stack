package com.bee32.plover.arch.util.dto;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.free.ClassLocal;

public class BeanPropertyAccessor
        implements IPropertyAccessor<Object, Object> {

    final Class<?> type;
    final Method getter;
    final Method setter;

    public BeanPropertyAccessor(PropertyDescriptor property) {
        type = property.getPropertyType();
        getter = property.getReadMethod();
        setter = property.getWriteMethod();
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Object get(Object obj) {
        try {
            return getter.invoke(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void set(Object obj, Object value) {
        try {
            setter.invoke(obj, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    static class PropertyMap
            extends HashMap<String, BeanPropertyAccessor> {

        private static final long serialVersionUID = 1L;

    }

    static final ClassLocal<PropertyMap> classPropertyMap;
    static {
        classPropertyMap = new ClassLocal<BeanPropertyAccessor.PropertyMap>();
    }

    public static synchronized PropertyMap parse(Class<?> beanClass)
            throws IntrospectionException {

        PropertyMap propertyMap = classPropertyMap.get(beanClass);

        if (propertyMap == null) {
            propertyMap = new PropertyMap();

            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
                BeanPropertyAccessor propertyAccessor = new BeanPropertyAccessor(property);
                propertyMap.put(property.getName(), propertyAccessor);
            }

            classPropertyMap.put(beanClass, propertyMap);
        }
        return propertyMap;
    }

    public static synchronized <S, T> IPropertyAccessor<S, T> access(//
            Class<? extends S> beanClass, String propertyName)
            throws IntrospectionException {

        PropertyMap propertyMap = parse(beanClass);

        return (IPropertyAccessor<S, T>) propertyMap.get(propertyName);
    }

}
