package com.bee32.plover.model.schema;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

public class BeanSchemaBuilder
        extends SchemaBuilder {

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public ISchema buildSchema(Class<?> type)
            throws SchemaBuilderException {
        if (type == null)
            throw new NullPointerException("type");

        BeanSchema schema = new BeanSchema();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                String name = propertyDescriptor.getName();
                Class<?> propertyType = propertyDescriptor.getPropertyType();

                PropertySchema property = new PropertySchema(name, propertyType);
                schema.add(property);
            }

            for (MethodDescriptor methodDescriptor : beanInfo.getMethodDescriptors()) {
                String name = methodDescriptor.getName();
                methodDescriptor.getMethod();
            }

        } catch (IntrospectionException e) {
            throw new SchemaBuilderException(e.getMessage(), e);
        }

        return schema;
    }

}
