package com.bee32.plover.site;

import java.lang.reflect.Method;

public class EnumUtil {

    public static <E extends Enum<?>> E[] values(Class<E> enumType) {
        if (!enumType.isEnum())
            throw new IllegalArgumentException("Not enumeration type: " + enumType);
        try {
            Method _values = enumType.getMethod("values");
            E[] values = (E[]) _values.invoke(null);
            return values;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
