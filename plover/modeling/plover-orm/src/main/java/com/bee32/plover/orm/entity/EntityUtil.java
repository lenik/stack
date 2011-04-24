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

    public static <E extends IEntity<K>, K extends Serializable> K parseIdOfEntity(Class<E> entityClass, String idString) {
        KeyTypeEnum keyTypeEnum = entityKeyTypeMap.get(entityClass);
        if (keyTypeEnum == null) {
            synchronized (EntityUtil.class) {
                if (keyTypeEnum == null) {
                    Class<K> keyType = getKeyType(entityClass);
                    keyTypeEnum = getTypeEnum(keyType);
                    if (keyTypeEnum == null)
                        throw new IllegalUsageException(//
                                "Unsupported primary key type of Entity " + entityClass + ": " + keyType);
                    entityKeyTypeMap.put(entityClass, keyTypeEnum);
                }
            }
        }
        return _parseId(keyTypeEnum, idString);
    }

    public static <K extends Serializable> K parseId(Class<K> keyType, String idString) {
        if (keyType == null)
            throw new NullPointerException("keyType");

        if (idString == null)
            return null;

        KeyTypeEnum keyTypeEnum = getTypeEnum(keyType);
        if (keyTypeEnum == null)
            throw new IllegalUsageException(//
                    "Unsupported primary key type: " + keyType);

        return _parseId(keyTypeEnum, idString);
    }

    static <K extends Serializable> K _parseId(KeyTypeEnum keyTypeEnum, String idString) {
        Serializable key;

        switch (keyTypeEnum) {
        case INT:
            if (idString.isEmpty())
                return null;
            key = Integer.parseInt(idString);
            break;

        case LONG:
            if (idString.isEmpty())
                return null;
            key = Long.parseLong(idString);
            break;

        case STRING:
        default:
            // XXX Should empty-string be converted to null?
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
