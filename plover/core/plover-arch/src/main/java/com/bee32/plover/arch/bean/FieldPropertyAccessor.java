package com.bee32.plover.arch.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.free.IllegalUsageException;

public class FieldPropertyAccessor
        implements IPropertyAccessor<Object>, Serializable {

    private static final long serialVersionUID = 1L;

    final Class<?> clazz;
    final String fieldName;

    transient Field field;

    public FieldPropertyAccessor(Class<?> clazz, String fieldName) {
        this(clazz, fieldName, null);
    }

    public FieldPropertyAccessor(Class<?> clazz, Field field) {
        this(clazz, field.getName(), field);
    }

    public FieldPropertyAccessor(Class<?> clazz, String fieldName, Field field) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (fieldName == null)
            throw new NullPointerException("fieldName");

        this.clazz = clazz;
        this.fieldName = fieldName;
        this.field = field;
    }

    public Field getField() {
        if (field == null) {
            synchronized (this) {
                if (field == null) {
                    Class<?> clazz = this.clazz;
                    while (clazz != null)
                        try {
                            field = clazz.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            break;
                        } catch (NoSuchFieldException e) {
                            clazz = clazz.getSuperclass();
                            continue;
                        }

                    if (clazz == null)
                        throw new IllegalUsageException("No field " + fieldName + " is declared in " + clazz);
                }
            }
        }
        return field;
    }

    @Override
    public Class<?> getType() {
        return getField().getType();
    }

    @Override
    public Object get(Object obj) {
        try {
            return getField().get(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void set(Object obj, Object value) {
        try {
            getField().set(obj, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
