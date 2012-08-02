package com.bee32.sem.term;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.res.INlsBundle;
import com.bee32.plover.arch.util.res.NlsBundles;

public class TermMetadata {

    Class<?> declaringClass;
    PropertyDescriptor declaringProperty;

    String name;
    String label;
    String description;

    public TermMetadata() {
    }

    public TermMetadata(Class<?> declaringClass, PropertyDescriptor declaringProperty, String name, String label,
            String description) {
        this.declaringClass = declaringClass;
        this.declaringProperty = declaringProperty;
        this.name = name;
        this.label = label;
        this.description = description;
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(Class<?> declaringClass) {
        this.declaringClass = declaringClass;
    }

    public PropertyDescriptor getDeclaringProperty() {
        return declaringProperty;
    }

    public void setDeclaringProperty(PropertyDescriptor declaringProperty) {
        this.declaringProperty = declaringProperty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ": " + label + " -- " + description;
    }

    public static TermMetadata fromTermProperty(Class<?> clazz, String propertyName) {
        Map<String, TermMetadata> metadataMap = getMetadataMap(clazz);
        TermMetadata metadata = metadataMap.get(propertyName);
        return metadata;
    }

    public static synchronized Map<String, TermMetadata> getMetadataMap(Class<?> clazz) {
        Map<String, TermMetadata> map = classLocalMetadataMap.get(clazz);
        if (map == null) {
            map = loadMetadataMap(clazz);
            classLocalMetadataMap.put(clazz, map);
        }
        return map;
    }

    static final ClassLocal<Map<String, TermMetadata>> classLocalMetadataMap = new ClassLocal<>();

    static Map<String, TermMetadata> loadMetadataMap(Class<?> clazz) {
        Map<String, TermMetadata> terms = new LinkedHashMap<>();

        String baseName = clazz.getName().replace('.', '/');
        INlsBundle bundle;
        BeanInfo beanInfo;

        try {
            bundle = NlsBundles.getBundle(baseName);
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (MissingResourceException e) {
            throw new IllegalUsageException(e.getMessage(), e);
        } catch (IntrospectionException e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }

        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            Method getter = property.getReadMethod();
            Term _term = getter.getAnnotation(Term.class);
            if (_term != null) {
                String propertyName = property.getName();
                String label = bundle.getString(propertyName + ".label");
                String description = bundle.getString(propertyName + ".description");

                TermMetadata term = new TermMetadata();
                term.setDeclaringClass(clazz);
                term.setDeclaringProperty(property);
                term.setName(propertyName);
                term.setLabel(label);
                term.setDescription(description);
                terms.put(propertyName, term);
            }
        }
        return terms;
    }

}
