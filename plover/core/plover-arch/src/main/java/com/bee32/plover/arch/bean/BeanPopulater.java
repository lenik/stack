package com.bee32.plover.arch.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import javax.free.Nullables;
import javax.free.ParserUtil;
import javax.free.ReadOnlyException;

import com.bee32.plover.arch.BuildException;

public class BeanPopulater {

    public static int populate(Object obj, Map<String, ?> struct)
            throws BuildException {
        return populate(obj.getClass(), obj, struct);
    }

    public static int populate(Class<?> type, Object obj, Map<String, ?> struct)
            throws BuildException {
        if (obj == null)
            throw new NullPointerException("obj");

        int changes = 0;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : properties) {
                String name = property.getName();
                if (!struct.containsKey(name))
                    continue;

                Class<?> propertyType = property.getPropertyType();
                Method readMethod = property.getReadMethod();

                Object user = struct.get(name);
                Object converted;

                if (user == null) {
                    converted = null;
                } else {
                    Class<?> userType = user.getClass();
                    if (userType == user) {
                        converted = user;
                    } else {
                        String text = String.valueOf(user);
                        converted = ParserUtil.parse(propertyType, text);
                    }
                }

                Object old = readMethod.invoke(obj);

                if (Nullables.equals(old, converted)) {
                    Method writeMethod = property.getWriteMethod();

                    if (writeMethod == null)
                        throw new ReadOnlyException("Property " + name + " of tyep " + propertyType);

                    writeMethod.invoke(obj, converted);
                    changes++;
                }
            }
        } catch (Exception e) {
            throw new BuildException(e.getMessage(), e);
        }

        return changes;
    }

}
