package com.bee32.plover.model.schema;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.ValidateException;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;
import com.bee32.plover.model.stereo.StereoType;

public interface ISchemaElement<T>
        extends IComponent, IQualified {

    StereoType getStereoType();

    Class<T> getType();

    PreferenceLevel getPreferenceLevel();

    T decodeText(String s)
            throws DecodeException;

    String encodeText(T value)
            throws EncodeException;

    void validate(T value)
            throws ValidateException;

}
