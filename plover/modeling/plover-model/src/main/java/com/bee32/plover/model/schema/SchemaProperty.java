package com.bee32.plover.model.schema;

import com.bee32.plover.model.stereo.StereoType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchemaProperty
        extends SchemaElement {

    public SchemaProperty(String name, Class propertyType) {
        super(name, StereoType.PROPERTY, propertyType);
    }

}
