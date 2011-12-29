package com.bee32.sem.uber.refactor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.bee32.plover.orm.util.EntityUsage;
import com.bee32.plover.orm.util.EntityUsageCollector;
import com.bee32.plover.orm.util.IEntityUsageProcessor;
import com.bee32.plover.orm.util.ITypeAbbrAware;

public class AbbrUsageProcessor
        implements IEntityUsageProcessor {

    @Override
    public void process(Class<?> entityType, EntityUsageCollector collector)
            throws Exception {
        if (!ITypeAbbrAware.class.isAssignableFrom(entityType))
            return;

        BeanInfo info = Introspector.getBeanInfo(entityType);
        List<String> abbrProperties = new ArrayList<String>();

        for (PropertyDescriptor property : info.getPropertyDescriptors()) {
            String propertyName = property.getName();
            if ("class".equals(propertyName))
                continue;

            Class<?> propertyType = property.getPropertyType();
            if (propertyType.equals(Class.class)) {
                String persistPropertyName = property.getName() + "Id";

                // We need xxxTypeId property to work.
                Method readMethod = property.getReadMethod();
                String persistMethodName = readMethod.getName() + "Id";
                Method persistMethod = getDeclaredMethod(entityType, persistMethodName);
                if (persistMethod == null)
                    continue;

                Column _column = persistMethod.getAnnotation(Column.class);
                if (_column != null) {
                    String nameOverride = _column.name();
                    if (!nameOverride.isEmpty())
                        persistPropertyName = nameOverride;
                }

                abbrProperties.add(persistPropertyName);
            }
        }

        if (!abbrProperties.isEmpty()) {
            EntityUsage usage = collector.getUsage(ITypeAbbrAware.USAGE_ID);
            StringBuilder description = new StringBuilder(abbrProperties.size() * 30);
            for (int i = 0; i < abbrProperties.size(); i++) {
                if (i != 0)
                    description.append(", ");
                description.append(abbrProperties.get(i));
            }
            usage.add(entityType, description.toString());
        }
    }

    static Method getDeclaredMethod(Class<?> type, String methodName, Class<?>... parameterTypes) {
        try {
            return type.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
        }
        Class<?> superclass = type.getSuperclass();
        if (superclass == null)
            return null;
        return getDeclaredMethod(superclass, methodName, parameterTypes);
    }

}