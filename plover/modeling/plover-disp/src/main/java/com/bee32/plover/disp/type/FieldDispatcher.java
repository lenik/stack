package com.bee32.plover.disp.type;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.ITokenQueue;

public class FieldDispatcher
        extends AbstractDispatcher {

    public FieldDispatcher() {
        super();
    }

    public FieldDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.FIELD_ORDER;
    }

    private transient ClassMap<String, Field> classMap;
    {
        classMap = new ClassMap<String, Field>();
    }

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        String fieldName = tokens.peek();
        if (fieldName == null)
            return null;

        Class<? extends Object> contextClass = context.getClass();

        Map<String, Field> fieldMap = classMap.get(contextClass);
        if (fieldMap == null) {
            fieldMap = new HashMap<String, Field>();

            Field[] fields = contextClass.getFields();
            for (Field f : fields)
                fieldMap.put(f.getName(), f);

            classMap.put(contextClass, fieldMap);
        }

        Field field = fieldMap.get(fieldName);
        if (field == null)
            return null;

        try {
            return field.get(context);
        } catch (Exception e) {
            throw new DispatchException(e);
        }
    }

}

class FieldKey {

    private final Class<?> clazz;
    private final String fieldName;

    public FieldKey(Class<?> clazz, String fieldName) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (fieldName == null)
            throw new NullPointerException("fieldName");
        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clazz.hashCode();
        result = prime * result + fieldName.hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        assert obj instanceof FieldKey;
        FieldKey other = (FieldKey) obj;
        if (!clazz.equals(other.clazz))
            return false;
        if (!fieldName.equals(other.fieldName))
            return false;
        return true;
    }

}
