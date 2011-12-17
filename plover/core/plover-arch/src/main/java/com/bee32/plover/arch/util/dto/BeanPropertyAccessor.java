package com.bee32.plover.arch.util.dto;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

public class BeanPropertyAccessor
        implements IPropertyAccessor<Object> {

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

    public static synchronized <bean_t, property_t> IPropertyAccessor<property_t> access(//
            Class<? extends bean_t> beanClass, String propertyName) {

        PropertyMap propertyMap;
        try {
            propertyMap = parse(beanClass);
        } catch (IntrospectionException e) {
            throw new IllegalUsageException(String.format("Bad property name: %s  for type %s", //
                    propertyName, beanClass.getName()), e);
        }

        return (IPropertyAccessor<property_t>) propertyMap.get(propertyName);
    }

}
