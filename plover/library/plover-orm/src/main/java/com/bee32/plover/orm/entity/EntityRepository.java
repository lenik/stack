package com.bee32.plover.orm.entity;

import java.lang.reflect.Modifier;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.ParserUtil;

import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.locator.IObjectLocator;
import com.bee32.plover.arch.locator.ObjectLocatorRegistry;

public abstract class EntityRepository<E extends IEntity<K>, K>
        extends Repository<K, E>
        implements IEntityRepository<E, K> {

    protected Class<? extends E> entityType;

    private IObjectLocator parentLocator;

    public EntityRepository(Class<E> instanceType, Class<K> keyType) {
        super(keyType, instanceType);
        init();
    }

    public EntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, keyType, instanceType);
        init();
    }

    @SuppressWarnings("unchecked")
    void init() {
        Class<?> entityType;
        try {
            entityType = getEntityType(instanceType);
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException("No implementation type for " + instanceType);
        }

        if (!instanceType.isAssignableFrom(entityType))
            throw new IllegalUsageException("Incompatible implementation " + entityType + " for " + instanceType);

        this.entityType = (Class<? extends E>) entityType;

        ObjectLocatorRegistry.getInstance().register(this);
    }

    public Class<? extends E> getEntityType() {
        return entityType;
    }

    @Override
    public K getKey(E entity) {
        return entity.getPrimaryKey();
    }

    @Override
    public abstract K save(E entity);

    @Override
    public abstract void update(E entity);

    @Override
    public abstract void refresh(E entity);

    // IObjectLocator

    protected K parseKey(String location)
            throws ParseException {
        if (keyType == String.class)
            return keyType.cast(location);
        return ParserUtil.parse(keyType, location);
    }

    protected String formatKey(K key) {
        return String.valueOf(key);
    }

    @Override
    public IObjectLocator getParent() {
        return parentLocator;
    }

    public void setParent(IObjectLocator parentLocator) {
        this.parentLocator = parentLocator;
    }

    @Override
    public Class<?> getBaseType() {
        return instanceType;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public Object locate(String location) {
        return retrieve(location);
    }

    @Override
    public boolean isLocatable(Object entity) {
        if (entity == null)
            return false;

        if (!instanceType.isInstance(entity))
            return false;

        return true;
    }

    @Override
    public String getLocation(Object entity) {
        E instance = instanceType.cast(entity);
        K key = instance.getPrimaryKey();
        String keyLocation = formatKey(key);
        return keyLocation;
    }

    protected Class<?> getEntityType(Class<?> clazz)
            throws ClassNotFoundException {

        int modifiers = clazz.getModifiers();
        if (!Modifier.isAbstract(modifiers))
            return clazz;

        String typeName = clazz.getName();
        int lastDot = typeName.lastIndexOf('.');
        String prefix;
        String simpleName;
        if (lastDot == -1) {
            prefix = "";
            simpleName = typeName;
        } else {
            prefix = typeName.substring(0, lastDot + 1);
            simpleName = typeName.substring(lastDot + 1);
        }

        String entityClassName = null;
        if (simpleName.length() >= 2) {
            char a = simpleName.charAt(0);
            char b = simpleName.charAt(1);
            if (a == 'I' && Character.isUpperCase(b))
                entityClassName = simpleName.substring(1);
            else
                entityClassName = simpleName + "Impl";
        } else
            entityClassName = simpleName + "Impl";

        entityClassName = prefix + entityClassName;

        return Class.forName(entityClassName, true, clazz.getClassLoader());
    }

}
