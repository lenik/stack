package com.bee32.plover.model.schema;

import com.bee32.plover.model.stereo.StereoType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertySchema
        extends AbstractSchema {

    public PropertySchema(String name, Class propertyType) {
        super(name, StereoType.PROPERTY, propertyType);
    }

}
