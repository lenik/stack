package com.bee32.plover.model.schema;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.ValidateException;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;
import com.bee32.plover.model.stereo.StereoType;

public interface ISchema
        extends IComponent, IQualified, Iterable<ISchema> {

    StereoType getStereoType();

    String getName();

    SchemaKey getKey();

    Class<?> getType();

    PreferenceLevel getPreferenceLevel();

    /**
     * Transient value SHOULD NOT be transferred to the view by default, it should be requested on
     * demand.
     *
     * @return <code>true</code> If the referred element is transient.
     */
    boolean isTransient();

    Object decodeText(String s, Object enclosingObject)
            throws DecodeException;

    String encodeText(Object value, Object enclosingObject)
            throws EncodeException;

    void validate(Object value, Object enclosingObject)
            throws ValidateException;

    Iterable<ISchema> restrict(StereoType stereoType);

    ISchema get(SchemaKey schemaKey);

    ISchema getProperty(String name);

}
