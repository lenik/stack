package com.bee32.plover.arch;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.servlet.ServletRequest;

import com.bee32.plover.arch.util.IStruct;

public interface IRepository<K, V>
        extends IComponent // IModel ??
{

    /**
     * Get the object key type.
     *
     * @return Non-<code>null</code> class of the key.
     */
    Class<K> getKeyType();

    /**
     * Get the object instance type.
     *
     * @return Non-<code>null</code> class of the instance.
     */
    Class<V> getInstanceType();

    /**
     * Get the primary key of an object.
     */
    K getKey(V obj);

    /**
     * @see Map#containsKey(Object)
     */
    boolean containsKey(Serializable key);

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
    V get(K key);

    /**
     * Retrieve an object by key.
     *
     * Assume the object is existed.
     *
     * @throws IllegalUsageException
     *             If object doesn't exist.
     */
    V load(K key);

    Collection<? extends K> listKeys();

    Collection<? extends V> list();

    /**
     * Create and populate a new instance from a struct.
     *
     * @return Non-<code>null</code> instantiated instance.
     */
    V populate(IStruct struct)
            throws BuildException;

    /**
     * Create and populate a new instance from a servlet request.
     *
     * @return Non-<code>null</code> instantiated instance.
     */
    V populate(ServletRequest request)
            throws BuildException;

    /**
     * Populate the existed instance with a struct.
     *
     * @return <code>true</code> if anything changed.
     */
    boolean populate(V instance, IStruct struct)
            throws BuildException;

    /**
     * Save the entity to the persistence layer.
     *
     * @see javax.persist.EntityManager#persist(Object)
     * @see
     */
    K save(V obj);

    /**
     * Update the entity in the persistence layer.
     *
     * @see javax.persist.EntityManager#persist(Object)
     */
    void update(V obj);

    /**
     * Revert the entity to the persisted state.
     *
     * @see javax.persist.EntityManager#refresh(Object)
     */
    void refresh(V obj);

    /**
     * Save the object to the underlying persistence layer.
     *
     * @param obj
     *            Non-<code>null</code> object object.
     * @see Map#put(Object, Object)
     */
    void saveOrUpdate(V obj);

    /**
     * Delete an object from underlying persistent layer by object key.
     *
     * @param key
     *            The object key, may be <code>null</code> if null-key is used.
     * @param obj
     *            Non-<code>null</code> object object.
     * @see Map#remove(Object)
     */
    void deleteByKey(K key);

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
