package com.bee32.plover.arch.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.service.ServicePrototypeLoader;

public class EnumAltRegistry {

    static Logger logger = LoggerFactory.getLogger(EnumAltRegistry.class);

    static final ClassLocal<Map<String, ? extends EnumAlt<?, ?>>> clNameMap = new ClassLocal<>();
    static final ClassLocal<Map<Object, ? extends EnumAlt<?, ?>>> clValueMap = new ClassLocal<>();

    public static synchronized <E extends EnumAlt<?, ?>> //
    Map<String, E> getNameMap(Class<E> enumType) {
        Map<String, E> nameMap = (Map<String, E>) clNameMap.get(enumType);
        if (nameMap == null) {
            nameMap = new LinkedHashMap<>();
            clNameMap.put(enumType, nameMap);
        }
        return nameMap;
    }

    public static synchronized <E extends EnumAlt<V, ?>, V extends Serializable> //
    Map<V, E> getValueMap(Class<E> enumType) {
        Map<Object, E> _valueMap = (Map<Object, E>) clValueMap.get(enumType);
        if (_valueMap == null) {
            _valueMap = new LinkedHashMap<>();
            clValueMap.put(enumType, _valueMap);
        }
        @SuppressWarnings("unchecked")
        Map<V, E> valueMap = (Map<V, E>) (Object) _valueMap;
        return valueMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    static void scanEnumTypes(boolean publicOnly)
            throws ClassNotFoundException, IOException {
        for (Class<?> enumType : ServicePrototypeLoader.load(EnumAlt.class, true)) {
            for (Field field : enumType.getDeclaredFields()) {
                // public static ...
                int mod = field.getModifiers();
                if (!Modifier.isStatic(mod))
                    continue;
                if (!Modifier.isPublic(mod))
                    if (publicOnly)
                        continue;
                    else
                        field.setAccessible(true);

                Class<?> declareType = (Class<?>) field.getType();
                if (!enumType.isAssignableFrom(declareType))
                    continue;

                EnumAlt<?, ?> entry;
                try {
                    entry = (EnumAlt<?, ?>) field.get(null);
                } catch (ReflectiveOperationException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }
                // Class<E> actualType = (Class<E>) entry.getClass();

                // logger.info("Register enum entry: " + entry.getName() + " of " + declareType);
                registerTree((Class) declareType, (EnumAlt) entry);
            }
        }
    }

    static <E extends EnumAlt<V, E>, V extends Serializable> //
    void registerTree(Class<E> enumType, E entry) {
        Map<String, E> nameMap = getNameMap(enumType);
        Map<V, E> valueMap = getValueMap(enumType);
        nameMap.put(entry.getName(), entry);
        valueMap.put(entry.getValue(), entry);

        Class<?> _superclass = enumType.getSuperclass();
        if (_superclass != null && EnumAlt.class.isAssignableFrom(_superclass)) {
            @SuppressWarnings("unchecked")
            Class<E> _superclass_xxx = (Class<E>) _superclass;
            registerTree(_superclass_xxx, entry);
        }
    }

    static boolean loaded;

    public static void loadConstants() {
        if (!loaded) {
            synchronized (EnumAltRegistry.class) {
                if (!loaded) {
                    try {
                        scanEnumTypes(true);
                    } catch (ClassNotFoundException | IOException e) {
                        throw new Error(e.getMessage(), e);
                    }
                    loaded = true;
                }
            }
        }
    }

}
