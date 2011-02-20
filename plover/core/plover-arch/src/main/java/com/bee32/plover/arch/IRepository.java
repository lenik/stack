package com.bee32.plover.arch;

import java.util.Map;

public interface IRepository<K, V> {

    Class<K> getKeyType();

    Class<V> getInstanceType();

    K getKey(V obj);

    /**
     * @see Map#containsKey(Object)
     */
    boolean containsKey(Object key);

    /**
     * @see Map#containsValue(Object)
     */
    boolean contains(Object obj);

    /**
     * Retrieve an object by key.
     *
     * @return The retrieved object, <code>null</code> if the specific key doesn't exist.
     * @see Map#get(Object)
     */
    V retrieve(Object key);

    void save(K key, V obj);

    void update(K key, V obj);

    /**
     * Save the object to the underlying persistence layer.
     *
     * @param obj
     *            Non-<code>null</code> object object.
     * @see Map#put(Object, Object)
     */
    void saveOrUpdate(K key, V obj);

    /**
     * Delete an object from underlying persistent layer by object key.
     *
     * @param key
     *            The object key, may be <code>null</code> if null-key is used.
     * @param obj
     *            Non-<code>null</code> object object.
     * @see Map#remove(Object)
     */
    void deleteByKey(Object key);

    /**
     * Delete an object from underlying persistent layer.
     *
     * @param obj
     *            Non-<code>null</code> object object.
     */
    void delete(Object obj);

    /**
     * @see Map#clear()
     */
    void deleteAll();

}
