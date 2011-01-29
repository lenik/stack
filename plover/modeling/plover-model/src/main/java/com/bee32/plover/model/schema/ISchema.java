package com.bee32.plover.model.schema;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.ValidateException;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;
import com.bee32.plover.model.stereo.StereoType;

public interface ISchema<T>
        extends IComponent, IQualified {

    StereoType getStereoType();

    Class<T> getType();

    PreferenceLevel getPreferenceLevel();

    /**
     * Transient value SHOULD NOT be transferred to the view by default, it should be requested on
     * demand.
     *
     * @return <code>true</code> If the referred element is transient.
     */
    boolean isTransient();

    T decodeText(String s, Object enclosingObject)
            throws DecodeException;

    String encodeText(T value, Object enclosingObject)
            throws EncodeException;

    void validate(T value, Object enclosingObject)
            throws ValidateException;

    Iterable<SchemaKey> listSubKeys();

    Iterable<? extends ISchema<?>> listSubSchemas();

    ISchema<?> getSubSchema(SchemaKey subKey);

}
