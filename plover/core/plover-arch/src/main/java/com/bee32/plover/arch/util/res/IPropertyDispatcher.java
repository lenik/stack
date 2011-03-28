package com.bee32.plover.arch.util.res;

import javax.free.IllegalUsageException;

public interface IPropertyDispatcher {

    /**
     * Get the root (default) property acceptor.
     *
     * @return <code>null</code> If root acceptor isn't defined.
     */
    IPropertyAcceptor getRootAcceptor();

    /**
     * Set the root (default) property acceptor.
     *
     * @return this.
     */
    PropertyDispatcher setRootAcceptor(IPropertyAcceptor acceptor);

    /**
     * Get the property acceptor for specifc prefix.
     *
     * @return <code>null</code> If the property acceptor for specific prefix isn't defined.
     */
    IPropertyAcceptor getPrefixAcceptor(String prefix);

    /**
     * Register a new prefix acceptor.
     *
     * <p>
     * Prefixes are not overlapped. That is, if the longest-matching-prefix are handled, then any
     * shorter-matching prefix acceptor are ignored.
     *
     * <p>
     * It's your responsibility to chain the acceptor to allow overlapping,
     *
     * @param prefix
     *            Only properties whose name starts with the prefix are accepted by the specific
     *            acceptor.
     * @return this.
     * @throws IllegalUsageException
     *             If there's existing acceptor with the same prefix.
     */
    PropertyDispatcher addPrefixAcceptor(String prefix, IPropertyAcceptor acceptor)
            throws IllegalUsageException;

    /**
     * Remove a specific prefix acceptor.
     *
     * @return <code>null</code> if the prefix isn't registered.
     */
    IPropertyAcceptor removePrefixAcceptor(String prefix);

    /**
     * Dispatch a property.
     * <p>
     * You can also explicitly dispatch a given proeprty by calling this method directly.
     *
     * @param key
     *            The key of property.
     * @param content
     *            The content of property.
     */
    void dispatch(String key, String content);

    /**
     * Get the source properties to be dispatched.
     *
     * @return Non-<code>null</code> properties.
     * @throws IllegalStateException
     *             If properties hasn't been set.
     */
    IProperties getProperties();

    /**
     * Set the source properties to be dispatched.
     *
     * @param properties
     *            Non-<code>null</code> properties.
     */
    void setProperties(IProperties properties);

    /**
     * Dispatch all properties defined in the bound resource.
     */
    void dispatch();

    /**
     * The same as {@link #dispatch()}, however, only dispatch once.
     */
    void pull();

    void addPropertyRefreshListener(IPropertyRefreshListener listener);

    void removePropertyRefreshListener(IPropertyRefreshListener listener);

}
