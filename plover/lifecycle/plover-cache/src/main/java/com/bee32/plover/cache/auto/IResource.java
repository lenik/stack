package com.bee32.plover.cache.auto;

import java.util.Collection;

import com.bee32.plover.cache.CacheCheckException;
import com.bee32.plover.cache.CacheRetrieveException;
import com.bee32.plover.cache.ref.CacheRef;

public interface IResource<T> {

    /**
     * Get the name of this resource.
     * <p>
     * The resource name is used for display purpose, so it need not to be unique.
     *
     * @return The resource name in string.
     */
    String getName();

    /**
     * Get the description of this resource.
     *
     * @return A text string describe this resource.
     */
    String getDescription();

    /**
     * Test if the resource is existed.
     *
     * @return <code>true</code> If this resource is existed.
     */
    boolean exists();

    /**
     * Get the version of this resource. The version is only meaningful if the resource is existed.
     *
     * @return A long integer represents the version of this resource.
     * @see #exists()
     */
    long getVersion();

    long getLastModifiedTime();

    /**
     * Get the dependent resources this resource depends on.
     *
     * @return A non-<code>null</code> collection contains all the immediately resources this
     *         resource depends on.
     */
    Collection<? extends IResource<?>> getDependencies();

    /**
     * Change the dependencies this resource depends on.
     * <p>
     * Change the dependencies on the fly is not thread safe, so you must protect it yourself.
     *
     * @param dependencies
     *            The new dependencies this resource will be depend on.
     * @throws NullPointerException
     *             If <code>dependencies</code> is <code>null</code>.
     */
    void setDependencies(Collection<? extends IResource<?>> dependencies);

    /**
     * Check if this resource is validated, means the underlying file was created, or the URL points
     * to a correct location.
     */
    boolean checkValidity()
            throws CacheCheckException;

    /**
     * Get the make schema configured for the auto build process.
     *
     * @return non-<code>null</code> instance of {@link IMakeSchema}.
     */
    IMakeSchema getSchema();

    /**
     * Make any dependency if they're out-dated, and then make myself.
     *
     * @return The lazy-created value this resource referrs to.
     */
    T pull()
            throws CacheRetrieveException;

    /**
     * Get the reference to the cached content.
     *
     * @return <code>null</code> if cache isn't used.
     */
    CacheRef<T> getCached();

}
