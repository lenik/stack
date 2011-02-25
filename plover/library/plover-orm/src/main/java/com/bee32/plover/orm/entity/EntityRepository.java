package com.bee32.plover.orm.entity;

import javax.free.ParseException;
import javax.free.ParserUtil;

import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.locator.IObjectLocator;
import com.bee32.plover.arch.locator.ObjectLocatorRegistry;

public abstract class EntityRepository<E extends IEntity<K>, K>
        extends Repository<K, E>
        implements IEntityRepository<E, K> {

    private IObjectLocator parentLocator;

    public EntityRepository(Class<E> entityType, Class<K> keyType) {
        super(keyType, entityType);
    }

    public EntityRepository(String name, Class<E> entityType, Class<K> keyType) {
        super(name, keyType, entityType);
    }

    {
        ObjectLocatorRegistry.getInstance().register(this);
    }

    public Class<E> getEntityType() {
        return instanceType;
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

}
