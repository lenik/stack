package com.bee32.plover.arch.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

import javax.free.ElTransformer;
import javax.free.English;
import javax.free.TransformedIterator;

public class BeanOfCollection {

    static boolean pluralForm = true;

    static String plural(String word) {
        if (pluralForm)
            word = English.pluralOf(word);
        return word;
    }

    public static <T> Object transform(Class<T> beanClass, Collection<? extends T> beans) {
        try {
            return transformMap(beanClass, beans);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> Map<String, ? extends Collection<?>> transformMap(Class<T> beanClass, Collection<?> beans)
            throws IntrospectionException {
        if (beanClass == null)
            throw new NullPointerException("beanClass");
        if (beans == null)
            throw new NullPointerException("beans");

        @SuppressWarnings("unchecked")
        Collection<? extends T> _beans = (Collection<? extends T>) beans;

        if (beans instanceof List<?>) {
            List<? extends T> beanList = (List<? extends T>) _beans;
            return transformMapOfList(beanClass, beanList);
        }

        if (beans instanceof Set<?>) {
            Set<? extends T> beanSet = (Set<? extends T>) _beans;
            return transformMapOfSet(beanClass, beanSet);
        }

        throw new UnsupportedOperationException("Unsupported collection type: " + beans.getClass());
    }

    public static <T> Map<String, List<?>> transformMapOfList(Class<T> beanClass, List<?> beanList)
            throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        Map<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            String name = plural(property.getName());
            EachPropertyList epl = new EachPropertyList(property, beanList);
            map.put(name, epl);
        }
        return map;
    }

    public static <T> Map<String, Set<?>> transformMapOfSet(Class<T> beanClass, Set<?> beanSet)
            throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        Map<String, Set<?>> map = new LinkedHashMap<String, Set<?>>();
        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            String name = plural(property.getName());
            EachPropertySet epl = new EachPropertySet(property, beanSet);
            map.put(name, epl);
        }
        return map;
    }

}

class EachPropertyList
        extends AbstractList<Object> {

    final List<?> beanList;
    final PropertyDescriptor property;

    public EachPropertyList(PropertyDescriptor property, List<?> beanList) {
        this.beanList = beanList;
        this.property = property;
    }

    @Override
    public int size() {
        if (beanList == null)
            return -1;
        else
            return beanList.size();
    }

    @Override
    public Object get(int index) {
        Method getter = property.getReadMethod();
        if (getter == null)
            throw new UnsupportedOperationException("No getter for property " + property);

        if (beanList == null)
            return null;

        Object bean = beanList.get(index);
        Object val;
        try {
            val = getter.invoke(bean);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return val;
    }

    @Override
    public Object set(int index, Object val) {
        Method setter = property.getWriteMethod();
        if (setter == null)
            throw new UnsupportedOperationException("No setter for property " + property);

        Object oldVal = get(index);

        if (beanList != null) {
            Object bean = beanList.get(index);
            try {
                setter.invoke(bean, val);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return oldVal;
    }

}

class EachPropertySet
        extends AbstractSet<Object> {

    final Set<?> beanSet;
    final PropertyDescriptor property;

    public EachPropertySet(PropertyDescriptor property, Set<?> beanSet) {
        this.beanSet = beanSet;
        this.property = property;
    }

    @Override
    public int size() {
        if (beanSet == null)
            return -1;
        else
            return beanSet.size();
    }

    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> _iterator = (Iterator<Object>) beanSet.iterator();
        return new TransformedIterator<Object, Object>(_iterator, new BeanPropertyProjection(property));
    }

}

class BeanPropertyProjection
        implements ElTransformer<Object, Object> {

    final PropertyDescriptor property;

    public BeanPropertyProjection(PropertyDescriptor property) {
        if (property == null)
            throw new NullPointerException("property");
        this.property = property;
    }

    @Override
    public Class<Object> getTransformedType() {
        return (Class<Object>) property.getPropertyType();
    }

    @Override
    public Class<Object> getUntransformedType() {
        return Object.class;
    }

    @Override
    public Object transform(Object bean) {
        Method getter = property.getReadMethod();
        if (getter == null)
            throw new UnsupportedOperationException("No getter for bean property " + property);
        Object propertyValue;
        try {
            propertyValue = getter.invoke(bean);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return propertyValue;
    }

    @Override
    public Object untransform(Object propertyValue) {
        throw new UnsupportedOperationException();
    }

}
