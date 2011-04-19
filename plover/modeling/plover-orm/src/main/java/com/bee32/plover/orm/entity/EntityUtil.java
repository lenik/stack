package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.ClassUtil;

enum KeyTypeEnum {

    INT,

    LONG,

    STRING,

}

public class EntityUtil {

    static final Map<Class<? extends IEntity<?>>, KeyTypeEnum> entityKeyTypeMap;
    static {
        entityKeyTypeMap = new HashMap<Class<? extends IEntity<?>>, KeyTypeEnum>();
    }

    public static <E extends IEntity<K>, K extends Serializable> K parseId(Class<E> entityClass, String idString) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");

        if (idString == null)
            return null;

        KeyTypeEnum keyTypeEnum = entityKeyTypeMap.get(entityClass);
        if (keyTypeEnum == null) {
            synchronized (EntityUtil.class) {
                if (keyTypeEnum == null) {
                    Class<K> keyType = getKeyType(entityClass);
                    keyTypeEnum = getTypeEnum(keyType);
                    if (keyTypeEnum == null)
                        throw new IllegalUsageException(//
                                "Unsupported primary key type of Entity " + entityClass + ": " + keyType);
                }
            }
        }

        Serializable key;

        switch (keyTypeEnum) {
        case INT:
            key = Integer.parseInt(idString);
            break;

        case LONG:
            key = Long.parseLong(idString);
            break;

        case STRING:
        default:
            key = idString;
        }

        @SuppressWarnings("unchecked")
        K _key = (K) key;
        return _key;
    }

    public static <E extends IEntity<K>, K extends Serializable> Class<K> getKeyType(Class<E> entityClass) {
        return ClassUtil.infer1(entityClass, IEntity.class, 0);
    }

    static KeyTypeEnum getTypeEnum(Class<?> type) {
        if (type == null)
            throw new NullPointerException("type");

        if (type == Integer.class)
            return KeyTypeEnum.INT;

        if (type == Long.class)
            return KeyTypeEnum.LONG;

        if (type == String.class)
            return KeyTypeEnum.STRING;

        return null;
    }

}
