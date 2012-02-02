package com.bee32.plover.arch.generic;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.arch.type.AbstractParameterizedType;
import com.bee32.plover.arch.util.ClassUtil;

public class BeanPropertiesParameterizedType
        extends AbstractParameterizedType {

    List<PropertyDescriptor> keyProperties = new ArrayList<PropertyDescriptor>();

    public BeanPropertiesParameterizedType(Class<?> baseType, String... keyPropertyNames)
            throws IntrospectionException {
        super(baseType);

        BeanInfo beanInfo = Introspector.getBeanInfo(baseType);
        for (String keyPropertyName : keyPropertyNames) {
            PropertyDescriptor keyProperty = null;
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
                String propertyName = property.getName();
                if (keyPropertyName.equals(propertyName)) {
                    keyProperty = property;
                    break;
                }
            }
            if (keyProperty == null)
                throw new IllegalArgumentException("Bad key property name: " + keyPropertyName);
            keyProperties.add(keyProperty);
        }
    }

    @Override
    protected void populateParameterMap(Object instance, Map<String, Object> parameterMap) {
        for (PropertyDescriptor keyProperty : keyProperties) {
            String paramName = keyProperty.getName();
            Object paramValue;
            Method getter = keyProperty.getReadMethod();
            try {
                paramValue = getter.invoke(instance);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            parameterMap.put(paramName, paramValue);
        }
    }

    @Override
    public String getDisplayTypeName(Object instance) {
        Class<? extends Object> staticType = instance.getClass();
        String staticName = ClassUtil.getTypeName(staticType);

        Map<String, Object> params = getParameters(instance);
        if (params.isEmpty())
            return staticName;

        StringBuilder buf = new StringBuilder();
        buf.append(staticName);
        buf.append("<");
        int index = 0;
        for (Entry<String, Object> param : params.entrySet()) {
            if (index++ != 0)
                buf.append(", ");
            buf.append(param.getKey());
            buf.append("=");
            buf.append(param.getValue());
        }
        buf.append(">");
        return buf.toString();
    }

}
