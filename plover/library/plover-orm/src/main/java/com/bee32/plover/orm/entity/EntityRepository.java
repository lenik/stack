package com.bee32.plover.orm.entity;

import javax.free.ParseException;

import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.locator.IObjectLocator;
import com.bee32.plover.arch.locator.ObjectLocatorRegistry;

public abstract class EntityRepository<E extends IEntity<K>, K>
        extends Repository<K, E>
        implements IObjectLocator {

    private IObjectLocator parentLocator;

    public EntityRepository(Class<E> entityType, Class<K> keyType) {
        super(keyType, entityType);
        ObjectLocatorRegistry.getInstance().register(this);
    }

    public Class<E> getEntityType() {
        return instanceType;
    }

    @Override
    public K getKey(E obj) {
        return obj.getPrimaryKey();
    }

    // IObjectLocator

    protected abstract K parseKey(String location)
            throws ParseException;

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
    public boolean isLocatable(Object obj) {
        if (obj == null)
            return false;

        if (!instanceType.isInstance(obj))
            return false;

        return true;
    }

    @Override
    public String getLocation(Object obj) {
        E instance = instanceType.cast(obj);
        K key = instance.getPrimaryKey();
        String keyLocation = formatKey(key);
        return keyLocation;
    }

}
