package com.bee32.plover.arch.util.dto;

import java.lang.reflect.Field;

import javax.free.IllegalUsageException;

public class FieldPropertyAccessor
        implements IPropertyAccessor<Object> {

    final Field field;

    public FieldPropertyAccessor(Field field) {
        if (field == null)
            throw new NullPointerException("field");
        this.field = field;
    }

    public FieldPropertyAccessor(Class<?> clazz, String fieldName) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (fieldName == null)
            throw new NullPointerException("fieldName");

        Field field = null;

        while (clazz != null)
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
                continue;
            }

        if (clazz == null)
            throw new IllegalUsageException("No field " + fieldName + " is declared in " + clazz);

        this.field = field;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public Object get(Object obj) {
        try {
            return field.get(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void set(Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
