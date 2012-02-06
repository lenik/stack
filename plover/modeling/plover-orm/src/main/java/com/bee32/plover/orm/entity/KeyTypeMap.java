package com.bee32.plover.orm.entity;

import javax.free.ClassLocal;

public class KeyTypeMap {

    static ClassLocal<Class<?>> keyTypeMap = new ClassLocal<Class<?>>();

    public static ClassLocal<Class<?>> getKeyTypeMap() {
        return keyTypeMap;
    }

    public static Class<?> getKeyType(Class<?> clazz) {
        return keyTypeMap.get(clazz);
    }

    public static void setKeyType(Class<?> clazz, Class<?> keyType) {
        keyTypeMap.put(clazz, keyType);
    }

}
