package com.bee32.plover.arch;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.servlet.ServletRequest;

import com.bee32.plover.arch.util.IStruct;

public interface IRepository<K, T>
        extends IComponent // IModel ??
{

    /**
     * Get the object key type.
     *
     * @return Non-<code>null</code> class of the key.
     */
    Class<? extends K> getKeyType();

    /**
     * Get the instance type.
     *
     * @return Non-<code>null</code> class of the instance.
     */
    Class<? extends T> getObjectType();

    /**
     * Get the primary key of an object.
     */
    K getKey(T obj);

    /**
     * Test if any object with the given key is existed in the repository.
     *
     * @see Map#containsKey(Object)
     */
    boolean containsKey(Object key);

    /**
     * Test if given object is existed in the repository.
     *
     * @see Map#containsValue(Object)
     */
    boolean contains(Object obj);

    /**
     * Retrieve an object by key.
     *
     * @return The retrieved object, <code>null</code> if the specific key doesn't exist.
     * @see Map#get(Object)
     */
    T get(K key);

    /**
     * Retrieve an object by key.
     *
     * Assume the object is existed.
     *
     * @throws IllegalUsageException
     *             If object doesn't exist.
     */
    T getOrFail(K key);

    Collection<K> keys();

    /**
     * List all instances.
     */
    List<T> list();

    /**
     * Create and populate a new instance from a struct.
     *
     * @return Non-<code>null</code> instantiated instance.
     */
    T populate(IStruct struct)
            throws BuildException;

    /**
     * Create and populate a new instance from a servlet request.
     *
     * @return Non-<code>null</code> instantiated instance.
     */
    T populate(ServletRequest request)
            throws BuildException;

    /**
     * Populate the existed instance with a struct.
     *
     * @return <code>true</code> if anything changed.
     */
    boolean populate(T obj, IStruct struct)
            throws BuildException;

    /**
     * Save the entity to the persistence layer.
     *
     * @see javax.persist.EntityManager#persist(Object)
     */
    K save(T obj);

    @SuppressWarnings("unchecked")
    void saveAll(T... objects);

    void saveAll(Collection<? extends T> objects);

    /**
     * Update the entity in the persistence layer.
     *
     * @see javax.persist.EntityManager#persist(Object)
     */
    void update(T obj);

    /**
     * Revert the entity to the persisted state.
     *
     * @see javax.persist.EntityManager#refresh(Object)
     */
    void refresh(T obj);

    /**
     * Save the object to the underlying persistence layer.
     *
     * @param obj
     *            Non-<code>null</code> object object.
     * @see Map#put(Object, Object)
     */
    void saveOrUpdate(T obj);

    @SuppressWarnings("unchecked")
    void saveOrUpdateAll(T... objects);

    void saveOrUpdateAll(Collection<? extends T> objects);

    /**
     * Delete an object from underlying persistent layer by object key.
     *
     * @param key
     *            The object key, may be <code>null</code> if null-key is used.
     * @param obj
     *            Non-<code>null</code> object object.
     * @return <code>true</code> if actually deleted.
     * @see Map#remove(Object)
     */
    boolean deleteByKey(K key);

    /**
     * Delete an object from underlying persistent layer.
     *
     * @param obj
     *            Non-<code>null</code> object object.
     * @return <code>true</code> if actually deleted.
     */
    boolean delete(Object obj);

    /**
     * @see Map#clear()
     */
    void deleteAll();

    int count();

}
