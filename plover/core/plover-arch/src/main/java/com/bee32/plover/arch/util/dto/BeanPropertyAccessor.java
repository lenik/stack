package com.bee32.plover.arch.util.dto;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;
import javax.free.ReadOnlyException;
import javax.free.UnexpectedException;

public class BeanPropertyAccessor
        implements IPropertyAccessor<Object>, Serializable {

    private static final long serialVersionUID = 1L;

    final Class<?> beanClass;
    final Class<?> type;
    final String getterMethodName;
    final String setterMethodName;
    final Class<?>[] getterMethodSign;
    final Class<?>[] setterMethodSign;

    transient Method getter;
    transient Method setter;

    public BeanPropertyAccessor(Class<?> beanClass, PropertyDescriptor property) {
        if (beanClass == null)
            throw new NullPointerException("beanClass");
        if (property == null)
            throw new NullPointerException("property");

        this.beanClass = beanClass;
        this.type = property.getPropertyType();

        Method getter = property.getReadMethod();
        Method setter = property.getWriteMethod();

        this.getter = getter;
        this.setter = setter;

        getterMethodName = getter.getName();
        getterMethodSign = getter.getParameterTypes();
        if (setter == null) {
            setterMethodName = null;
            setterMethodSign = null;
        } else {
            this.setterMethodName = setter.getName();
            this.setterMethodSign = setter.getParameterTypes();
        }
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    public Method getGetter() {
        if (getter == null) {
            synchronized (this) {
                if (getter == null) {
                    try {
                        getter = beanClass.getMethod(getterMethodName, getterMethodSign);
                    } catch (ReflectiveOperationException e) {
                        throw new UnexpectedException(e.getMessage(), e);
                    }
                }
            }
        }
        return getter;
    }

    public Method getSetter() {
        if (setterMethodName == null)
            throw new ReadOnlyException("Property is read-only: " + setterMethodName);

        if (setter == null) {
            synchronized (this) {
                if (setter == null) {
                    try {
                        setter = beanClass.getMethod(setterMethodName, setterMethodSign);
                    } catch (ReflectiveOperationException e) {
                        throw new UnexpectedException(e.getMessage(), e);
                    }
                }
            }
        }
        return setter;
    }

    @Override
    public Object get(Object obj) {
        try {
            return getGetter().invoke(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void set(Object obj, Object value) {
        try {
            getSetter().invoke(obj, value);
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
                BeanPropertyAccessor propertyAccessor = new BeanPropertyAccessor(beanClass, property);
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
