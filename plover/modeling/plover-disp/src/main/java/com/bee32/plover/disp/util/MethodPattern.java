package com.bee32.plover.disp.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

import overlay.OverlayUtil;

public class MethodPattern {

    private final Class<?>[] pattern;

    public MethodPattern(Class<?>... pattern) {
        if (pattern == null)
            throw new NullPointerException("pattern");
        this.pattern = pattern;
    }

    public Map<String, Method> searchOverlayMethods(Class<?> clazz, String extension) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (extension == null)
            throw new NullPointerException("extension");

        Map<String, Method> all = new HashMap<String, Method>();
        Map<String, Method> each = new HashMap<String, Method>();

        while (clazz != null) {
            Class<?> overlay = OverlayUtil.getOverlay(clazz, extension);

            if (overlay != null) {
                searchMethods(each, false, overlay);
                all.putAll(each);
                each.clear();
            }

            clazz = clazz.getSuperclass();
        }

        return all;
    }

    public Map<String, Method> searchMethods(Class<?> clazz) {
        Map<String, Method> methods = new HashMap<String, Method>();
        searchMethods(methods, false, clazz);
        return methods;
    }

    public void searchMethods(Map<String, Method> all, boolean canOverride, Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (!isAssignable(pattern, method.getParameterTypes()))
                continue;

            String methodName = method.getName();

            if (!canOverride) {
                Method existing = all.get(method.getName());
                if (existing != null)
                    throw new IllegalUsageException("Ambiguous methods: " + existing + " and " + method);
            }

            all.put(methodName, method);
        }
    }

    static boolean isAssignable(Class<?>[] lv, Class<?>[] rv) {
        if (lv.length != rv.length)
            return false;

        for (int i = 0; i < lv.length; i++)
            if (!lv[i].isAssignableFrom(rv[i]))
                return false;

        return true;
    }

}
