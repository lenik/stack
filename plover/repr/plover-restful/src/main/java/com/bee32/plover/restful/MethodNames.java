package com.bee32.plover.restful;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;

public class MethodNames {

    public static final String READ = null;

    public static final String CREATE = "create";
    public static final String CREATE_FORM = "new";

    public static final String UPDATE = "update";
    public static final String UPDATE_FORM = "edit";

    public static final String DELETE = "delete";

    public static final String ESTATE = "head";

    static Map<String, String> reverseMap;
    static {
        reverseMap = new HashMap<String, String>();

        for (Field field : MethodNames.class.getFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers))
                continue;

            if (!Modifier.isStatic(modifiers))
                continue;

            String name = field.getName();
            String value;
            try {
                value = (String) field.get(null);
            } catch (Exception e) {
                throw new UnexpectedException("read static field " + field, e);
            }

            if (value != null)
                reverseMap.put(value, name);
        }
    }

    public static boolean isDefined(String name) {
        return reverseMap.containsKey(name);
    }

}
