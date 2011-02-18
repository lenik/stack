package com.bee32.plover.model.schema;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.ValidateException;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;
import com.bee32.plover.model.stereo.StereoType;

public interface ISchema
        extends IComponent, IQualified, Iterable<ISchema> {

    /**
     * Get the stereo type of the element this schema refers to.
     *
     * @return The stereo type of the element this schema refers to.
     */
    StereoType getStereoType();

    /**
     * Get the name of the schema element.
     *
     * Both stereo-type and the name forms the schema key.
     *
     * @return Non-<code>null</code> name.
     */
    String getName();

    /**
     * Get the schema key which stands for this schema element.
     * <p>
     * Both stereo-type and the name forms the schema key.
     *
     * @return Non-<code>null</code> {@link SchemaKey}.
     */
    SchemaKey getKey();

    /**
     * Get the element class this schema refers to.
     *
     * @return The element class if available.
     */
    Class<?> getType();

    /**
     * Get the preference level for this schema element.
     *
     * The preference level specify the level of verbose to display.
     *
     * @return Non-<code>null</code> preference level constant.
     */
    PreferenceLevel getPreferenceLevel();

    /**
     * Transient value SHOULD NOT be transferred to the view by default, it should be requested on
     * demand.
     *
     * @return <code>true</code> If the referred element is transient.
     */
    boolean isTransient();

    /**
     * Convert the object from a string form.
     *
     * @return The decoded object value.
     */
    Object decodeText(String s, Object enclosingObject)
            throws DecodeException;

    /**
     * Convert the object to a string form.
     *
     * @return The encoded string for an object value.
     */
    String encodeText(Object value, Object enclosingObject)
            throws EncodeException;

    /**
     * Validate if the object is in a consistent state.
     *
     * @throws ValidateException
     *             If the object isn't validated.
     */
    void validate(Object value, Object enclosingObject)
            throws ValidateException;

    /**
     * Select children elements of a specific stereo type.
     *
     * @return Children elements for iteration-only of a specific stereo type.
     */
    Iterable<ISchema> restrict(StereoType stereoType);

    /**
     * Get schema of child element.
     *
     * @return The schema of the child element if existed, or <code>null</code>.
     */
    ISchema get(SchemaKey schemaKey);

    /**
     * Get schema of child property element.
     * <p>
     * This is the same to {@link #get(SchemaKey)} with {@link StereoType#PROPERTY}.
     *
     * @return The schema of the child property element if existed, or <code>null</code>.
     */
    ISchema getProperty(String name);

}
