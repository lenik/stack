package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

public interface INlsBundle {

    /**
     * Gets a string for the given key from this resource bundle or one of its parents. Calling this
     * method is equivalent to calling <blockquote>
     * <code>(String) {@link #getObject(java.lang.String) getObject}(key)</code>. </blockquote>
     *
     * @param key
     *            the key for the desired string
     * @exception NullPointerException
     *                if <code>key</code> is <code>null</code>
     * @exception MissingResourceException
     *                if no object for the given key can be found
     * @exception ClassCastException
     *                if the object found for the given key is not a string
     * @return the string for the given key
     */
    String getString(String key);

    /**
     * Gets a string array for the given key from this resource bundle or one of its parents.
     * Calling this method is equivalent to calling <blockquote>
     * <code>(String[]) {@link #getObject(java.lang.String) getObject}(key)</code>. </blockquote>
     *
     * @param key
     *            the key for the desired string array
     * @exception NullPointerException
     *                if <code>key</code> is <code>null</code>
     * @exception MissingResourceException
     *                if no object for the given key can be found
     * @exception ClassCastException
     *                if the object found for the given key is not a string array
     * @return the string array for the given key
     */
    String[] getStringArray(String key);

    /**
     * Gets an object for the given key from this resource bundle or one of its parents. This method
     * first tries to obtain the object from this resource bundle using
     * {@link #handleGetObject(java.lang.String) handleGetObject}. If not successful, and the parent
     * resource bundle is not null, it calls the parent's <code>getObject</code> method. If still
     * not successful, it throws a MissingResourceException.
     *
     * @param key
     *            the key for the desired object
     * @exception NullPointerException
     *                if <code>key</code> is <code>null</code>
     * @exception MissingResourceException
     *                if no object for the given key can be found
     * @return the object for the given key
     */
    Object getObject(String key);

    /**
     * Returns the locale of this resource bundle. This method can be used after a call to
     * getBundle() to determine whether the resource bundle returned really corresponds to the
     * requested locale or is a fallback.
     *
     * @return the locale of this resource bundle
     */
    Locale getLocale();

    /**
     * Returns an enumeration of the keys.
     *
     * @return an <code>Enumeration</code> of the keys contained in this <code>ResourceBundle</code>
     *         and its parent bundles.
     */
    Enumeration<String> getKeys();

    /**
     * Determines whether the given <code>key</code> is contained in this
     * <code>ResourceBundle</code> or its parent bundles.
     *
     * @param key
     *            the resource <code>key</code>
     * @return <code>true</code> if the given <code>key</code> is contained in this
     *         <code>ResourceBundle</code> or its parent bundles; <code>false</code> otherwise.
     * @exception NullPointerException
     *                if <code>key</code> is <code>null</code>
     * @since 1.6
     */
    boolean containsKey(String key);

    /**
     * Returns a <code>Set</code> of all keys contained in this <code>ResourceBundle</code> and its
     * parent bundles.
     *
     * @return a <code>Set</code> of all keys contained in this <code>ResourceBundle</code> and its
     *         parent bundles.
     * @since 1.6
     */
    Set<String> keySet();

}
