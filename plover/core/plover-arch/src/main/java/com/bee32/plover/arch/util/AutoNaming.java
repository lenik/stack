package com.bee32.plover.arch.util;

import java.util.HashMap;
import java.util.Map;

import javax.free.DisplayNameUtil;
import javax.free.Strings;

public class AutoNaming {

    static transient Map<Class<?>, String> dispNames;

    public static synchronized String getAutoName(Class<?> clazz) {
        if (dispNames == null)
            dispNames = new /* Weak */HashMap<Class<?>, String>();

        String dispName = dispNames.get(clazz);
        if (dispName == null) {
            dispName = _getAutoName(clazz);
            dispNames.put(clazz, dispName);
        }

        return dispName;
    }

    static String _getAutoName(Class<?> clazz) {
        String stem = DisplayNameUtil.getClassStemName(clazz);
        String dispName = Strings.hyphenatize(stem);
        return dispName;
    }

}
