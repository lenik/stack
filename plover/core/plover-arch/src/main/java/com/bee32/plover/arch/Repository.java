package com.bee32.plover.arch;

import java.lang.reflect.Type;

import javax.free.IllegalUsageException;
import javax.servlet.ServletRequest;

import com.bee32.plover.arch.util.BeanPopulater;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.inject.NotAComponent;

@NotAComponent
public abstract class Repository<K, T>
        extends Component
        implements IRepository<K, T> {

    protected Class<K> keyType;
    protected Class<T> objectType;

    public Repository() {
        super();
        introspect();
    }

    public Repository(String name) {
        super(name);
        introspect();
    }

    public Repository(Class<K> keyType, Class<T> objectType) {
        super();

        if (keyType == null)
            throw new NullPointerException("keyType");
        if (objectType == null)
            throw new NullPointerException("objType");

        this.keyType = keyType;
        this.objectType = objectType;
    }

    public Repository(String name, Class<K> keyType, Class<T> objectType) {
        super(name);

        if (keyType == null)
            throw new NullPointerException("keyType");
        if (objectType == null)
            throw new NullPointerException("objectType");

        this.keyType = keyType;
        this.objectType = objectType;
    }

    protected void introspect() {
        Type[] repositoryArgs = ClassUtil.getTypeArgs(getClass(), Repository.class);
        keyType = ClassUtil.bound1(repositoryArgs[0]);
        objectType = ClassUtil.bound1(repositoryArgs[1]);
    }

    @Override
    public boolean contains(Object obj) {
        T _obj = objectType.cast(obj);
        K key = getKey(_obj);
        return containsKey(key);
    }

    @Override
    public T load(K key) {
        T value = get(key);
        if (value == null)
            throw new IllegalUsageException("No record with key=" + key);
        return value;
    }

    @Override
    public void delete(Object obj) {
        T instance = objectType.cast(obj);
        K key = getKey(instance);
        deleteByKey(key);
    }

    @Override
    public void saveOrUpdate(T obj) {
        K key = getKey(obj);
        if (containsKey(key))
            update(obj);
        else
            save(obj);
    }

    @Override
    public T populate(IStruct struct)
            throws BuildException {
        T instance;
        try {
            instance = objectType.newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(instance, struct);
        return instance;
    }

    @Override
    public T populate(ServletRequest request)
            throws BuildException {
        T instance;
        try {
            instance = objectType.newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(instance, new MapStruct(request.getParameterMap()));
        return instance;
    }

    @Override
    public boolean populate(T obj, IStruct struct)
            throws BuildException {
        int changes = BeanPopulater.populate(objectType, obj, struct);
        return changes != 0;
    }

}
