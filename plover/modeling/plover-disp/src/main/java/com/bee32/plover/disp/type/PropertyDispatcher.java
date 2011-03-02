package com.bee32.plover.disp.type;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchContext;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IDispatchContext;
import com.bee32.plover.disp.util.ITokenQueue;

public class PropertyDispatcher
        extends AbstractDispatcher {

    public PropertyDispatcher() {
        super();
    }

    public PropertyDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.FIELD_ORDER;
    }

    private transient ClassMap<String, PropertyDescriptor> classMap;
    {
        classMap = new ClassMap<String, PropertyDescriptor>();
    }

    @Override
    public IDispatchContext dispatch(IDispatchContext context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getObject();
        if (obj == null)
            return null;

        String propertyName = tokens.peek();
        if (propertyName == null)
            return null;

        Class<? extends Object> contextClass = obj.getClass();

        Map<String, PropertyDescriptor> propertyMap = classMap.get(contextClass);
        if (propertyMap == null) {
            propertyMap = new HashMap<String, PropertyDescriptor>();

            PropertyDescriptor[] propertyDescriptors;
            try {
                propertyDescriptors = Introspector.getBeanInfo(contextClass).getPropertyDescriptors();
            } catch (IntrospectionException e) {
                throw new DispatchException(e.getMessage(), e);
            }
            for (PropertyDescriptor p : propertyDescriptors)
                propertyMap.put(p.getName(), p);

            classMap.put(contextClass, propertyMap);
        }

        PropertyDescriptor propertyDescriptor = propertyMap.get(propertyName);
        if (propertyDescriptor == null)
            return null;

        tokens.shift();

        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null)
            throw new DispatchException("Property " + propertyName + " isn't readable");

        Object result;
        try {
            result = readMethod.invoke(obj);
        } catch (Exception e) {
            throw new DispatchException(e);
        }

        return new DispatchContext(context, result, propertyName);
    }

}
