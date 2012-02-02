package com.bee32.plover.arch;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.servlet.ServletRequest;

import com.bee32.plover.arch.bean.BeanPopulater;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.inject.NotAComponent;

@NotAComponent
public abstract class Repository<K, T>
        extends Component
        implements IRepository<K, T> {

    protected Class<? extends K> keyType;
    protected Class<? extends T> objectType;

    public Repository() {
        super();
        introspect();
    }

    public Repository(String name) {
        super(name);
        introspect();
    }

    public Repository(Class<? extends K> keyType, Class<? extends T> objectType) {
        super();

        if (keyType == null)
            throw new NullPointerException("keyType");
        if (objectType == null)
            throw new NullPointerException("objType");

        this.keyType = keyType;
        this.objectType = objectType;

        introspect();
    }

    public Repository(String name, Class<? extends K> keyType, Class<? extends T> objectType) {
        super(name);

        if (keyType == null)
            throw new NullPointerException("keyType");
        if (objectType == null)
            throw new NullPointerException("objectType");

        this.keyType = keyType;
        this.objectType = objectType;

        introspect();
    }

    protected void introspect() {
        if (objectType != null)
            return;

        Class<?> repoClass = getClass();
        // Class<?> repoClass = ClassUtil.skipProxies(getClass());

        Type[] repositoryArgs = ClassUtil.getTypeArgs(repoClass, Repository.class);
        keyType = ClassUtil.bound1(repositoryArgs[0]);
        objectType = ClassUtil.bound1(repositoryArgs[1]);
        if (objectType == null)
            throw new NullPointerException("objectType");
    }

    @Override
    public Class<? extends K> getKeyType() {
        return keyType;
    }

    @Override
    public Class<? extends T> getObjectType() {
        return objectType;
    }

    @Override
    public boolean contains(Object obj) {
        T _obj = objectType.cast(obj);
        K key = getKey(_obj);
        return containsKey(key);
    }

    @Override
    public T getOrFail(K key) {
        T value = get(key);
        if (value == null)
            throw new IllegalUsageException("No record with key=" + key);
        return value;
    }

    @SafeVarargs
    public final void saveAll(T... objects) {
        List<T> list = Arrays.asList(objects);
        saveAll(list);
    }

    @Override
    public void saveOrUpdate(T obj) {
        K key = getKey(obj);
        if (containsKey(key))
            update(obj);
        else
            save(obj);
    }

    @SafeVarargs
    @Override
    public final void saveOrUpdateAll(T... objects) {
        List<T> list = Arrays.asList(objects);
        saveOrUpdateAll(list);
    }

    @Override
    public void saveAll(Collection<? extends T> objects) {
        for (T obj : objects)
            save(obj);
    }

    @Override
    public void saveOrUpdateAll(Collection<? extends T> objects) {
        for (T obj : objects)
            saveOrUpdate(obj);
    }

    @Override
    public boolean delete(Object obj) {
        T instance = objectType.cast(obj);
        K key = getKey(instance);
        return deleteByKey(key);
    }

    @Override
    public int deleteAll(Collection<?> objects) {
        int counter = 0;
        for (Object obj : objects)
            if (delete(obj))
                counter++;
        return counter;
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
