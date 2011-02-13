package com.bee32.plover.repr.cascade;

/**
 * Instance Inheritable
 *
 * Class inherits object.
 *
 * <h3>Transient Properties</h3>
 *
 * Transient properties are usually generated from other properties, it's different to optional
 * properties which have a default value generated from other properties: The transient property is
 * read-only, and won't accept any change. Set to a transient property has no effect.
 *
 * <p>
 * Using transient property instead of property with a default value will get a more consistent
 * state, because no extra reduntant is introduced. So, the computation behind should be fast. It's
 * also suggested to cache the generated value if the computation is expensive, when cache is used,
 * the cache should not be changed by others.
 *
 * <h4>Example</h4>
 * <ul>
 * <li>A font data object for specific font family and style, which holds the allocated font
 * resource.
 * </ul>
 */
public interface Overridable<T> {

    T override();

}
