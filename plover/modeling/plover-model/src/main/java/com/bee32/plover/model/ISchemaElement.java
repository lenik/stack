package com.bee32.plover.model;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.ValidateException;

import com.bee32.plover.arch.IComponent;

public interface ISchemaElement<T>
        extends IComponent {

    SchemaElementStereoType getStereoType();

    Class<T> getType();

    PreferenceLevel getPreferenceLevel();

    String getAnchor();

    T decodeText(String s)
            throws DecodeException;

    String encodeText(T value)
            throws EncodeException;

    void validate(T value)
            throws ValidateException;

}
