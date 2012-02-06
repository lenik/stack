package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.ParseException;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.arch.type.FriendTypes;
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

    public static <E extends Entity<K>, K extends Serializable> K parseIdOfEntity(Class<E> entityClass, String idString)
            throws ParseException {

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

    /**
     * Parse entity id in the standard format.
     *
     * @param keyType
     *            The key type, only Integer/Long/String is supported here.
     * @param idString
     *            Id string, , a <code>null</code> or empty string means null.
     * @return <code>null</code> If the given id string is <code>null</code>.
     */
    public static <K extends Serializable> K parseId(Class<K> keyType, String idString)
            throws ParseException {

        if (keyType == null)
            throw new NullPointerException("keyType");

        KeyTypeEnum keyTypeEnum = getTypeEnum(keyType);
        if (keyTypeEnum == null)
            throw new IllegalUsageException(//
                    "Unsupported primary key type: " + keyType);

        return _parseId(keyTypeEnum, idString);
    }

    static <K extends Serializable> K _parseId(KeyTypeEnum keyTypeEnum, String idString)
            throws ParseException {
        Serializable key;

        if (idString == null || idString.isEmpty())
            return null;

        switch (keyTypeEnum) {
        case INT:
            if (idString.isEmpty())
                return null;
            try {
                key = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                throw new ParseException("Bad id (int): " + idString);
            }
            break;

        case LONG:
            if (idString.isEmpty())
                return null;
            try {
                key = Long.parseLong(idString);
            } catch (NumberFormatException e) {
                throw new ParseException("Bad id (long): " + idString);
            }
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

    public static <E extends Entity<? extends K>, K extends Serializable> Class<K> getKeyType(Class<E> entityClass) {
        Class<K> keyType = (Class<K>) KeyTypeMap.getKeyType(entityClass);
        if (keyType == null) {
            keyType = ClassUtil.infer1(entityClass, Entity.class, 0);
            KeyTypeMap.setKeyType(entityClass, keyType);
        }
        return keyType;
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

    /**
     * Get any DTO type for the nearest ancestor of the given entity type.
     *
     * @return <code>null</code> if no DTO type found.
     */
    public static Class<?> getDtoType(Class<?> entityType) {
        return FriendTypes.getInheritedFriendType(entityType, "dto");
    }

    /**
     * Get any DTO type for the nearest ancestor of the given entity type.
     *
     * @return <code>null</code> if no DTO type found.
     */
    public static Class<?> getDaoType(Class<?> entityType) {
        return FriendTypes.getInheritedFriendType(entityType, "dao");
    }

    /**
     * Get any DTO type for the nearest ancestor of the given entity type.
     *
     * @return <code>null</code> if no DTO type found.
     */
    public static Class<?> getDsoType(Class<?> entityType) {
        return FriendTypes.getInheritedFriendType(entityType, "dso");
    }

    public static Class<?> getControllerType(Class<?> entityType) {
        return FriendTypes.getInheritedFriendType(entityType, "controller");
    }

    public static String getControllerPrefix(Class<?> entityType) {
        Class<?> controllerType = getControllerType(entityType);
        if (controllerType == null)
            return null;

        RequestMapping _requestMapping = controllerType.getAnnotation(RequestMapping.class);
        if (_requestMapping == null)
            return null;

        String[] paths = _requestMapping.value();
        if (paths.length == 1)
            return paths[0];

        return null;
    }

    public static EntitySelection select(Entity<?>... entities) {
        return new EntitySelection(entities);
    }

    public static EntitySelection select(Collection<Entity<?>> entities) {
        return new EntitySelection(entities);
    }

}
