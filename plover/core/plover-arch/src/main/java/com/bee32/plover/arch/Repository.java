package com.bee32.plover.arch;

import java.io.Serializable;
import java.lang.reflect.Type;

import javax.free.IllegalUsageException;
import javax.servlet.ServletRequest;

import com.bee32.plover.arch.util.BeanPopulater;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.inject.NotAComponent;

@NotAComponent
public abstract class Repository<K extends Serializable, V>
        extends Component
        implements IRepository<K, V> {

    protected Class<K> keyType;
    protected Class<V> valueType;

    public Repository() {
        super();
        introspect();
    }

    public Repository(String name) {
        super(name);
        introspect();
    }

    public Repository(Class<K> keyType, Class<V> valueType) {
        super();

        if (keyType == null)
            throw new NullPointerException("keyType");
        if (valueType == null)
            throw new NullPointerException("valueType");

        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Repository(String name, Class<K> keyType, Class<V> valueType) {
        super(name);

        if (keyType == null)
            throw new NullPointerException("keyType");
        if (valueType == null)
            throw new NullPointerException("valueType");

        this.keyType = keyType;
        this.valueType = valueType;
    }

    protected void introspect() {
        Type[] repositoryArgs = ClassUtil.getTypeArgs(getClass(), Repository.class);
        keyType = ClassUtil.bound1(repositoryArgs[0]);
        valueType = ClassUtil.bound1(repositoryArgs[1]);
    }

    @Override
    public Class<K> getKeyType() {
        return keyType;
    }

    @Override
    public Class<V> getInstanceType() {
        return valueType;
    }

    @Override
    public boolean contains(Object obj) {
        V instance = valueType.cast(obj);
        K key = getKey(instance);
        return containsKey(key);
    }

    @Override
    public V load(K key) {
        V value = get(key);
        if (value == null)
            throw new IllegalUsageException("No record with key=" + key);
        return value;
    }

    @Override
    public void delete(Object obj) {
        V instance = valueType.cast(obj);
        K key = getKey(instance);
        deleteByKey(key);
    }

    @Override
    public void saveOrUpdate(V obj) {
        K key = getKey(obj);
        if (containsKey(key))
            update(obj);
        else
            save(obj);
    }

    @Override
    public V populate(IStruct struct)
            throws BuildException {
        V instance;
        try {
            instance = valueType.newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(instance, struct);
        return instance;
    }

    @Override
    public V populate(ServletRequest request)
            throws BuildException {
        V instance;
        try {
            instance = valueType.newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(instance, new MapStruct(request.getParameterMap()));
        return instance;
    }

    @Override
    public boolean populate(V instance, IStruct struct)
            throws BuildException {
        int changes = BeanPopulater.populate(valueType, instance, struct);
        return changes != 0;
    }

}
