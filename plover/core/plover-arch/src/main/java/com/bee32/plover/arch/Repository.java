package com.bee32.plover.arch;

import com.bee32.plover.arch.util.BeanPopulater;
import com.bee32.plover.arch.util.IStruct;

public abstract class Repository<K, V>
        extends Component
        implements IRepository<K, V> {

    protected final Class<K> keyType;
    protected final Class<V> instanceType;

    public Repository(Class<K> keyType, Class<V> instanceType) {
        super();
        if (keyType == null)
            throw new NullPointerException("keyType");
        if (instanceType == null)
            throw new NullPointerException("instanceType");
        this.keyType = keyType;
        this.instanceType = instanceType;
    }

    public Repository(String name, Class<K> keyType, Class<V> instanceType) {
        super(name);
        if (keyType == null)
            throw new NullPointerException("keyType");
        if (instanceType == null)
            throw new NullPointerException("instanceType");
        this.keyType = keyType;
        this.instanceType = instanceType;
    }

    @Override
    public Class<K> getKeyType() {
        return keyType;
    }

    @Override
    public Class<V> getInstanceType() {
        return instanceType;
    }

    @Override
    public boolean contains(Object obj) {
        V instance = instanceType.cast(obj);
        K key = getKey(instance);
        return containsKey(key);
    }

    @Override
    public void delete(Object obj) {
        V instance = instanceType.cast(obj);
        K key = getKey(instance);
        deleteByKey(key);
    }

    @Override
    public K saveOrUpdate(V obj) {
        K key = getKey(obj);
        if (containsKey(key))
            update(obj);
        else
            save(obj);
        return key;
    }

    @Override
    public V populate(IStruct struct)
            throws BuildException {
        V instance;
        try {
            instance = instanceType.newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(instance, struct);
        return instance;
    }

    @Override
    public boolean populate(V instance, IStruct struct)
            throws BuildException {
        int changes = BeanPopulater.populate(instanceType, instance, struct);
        return changes != 0;
    }

}
