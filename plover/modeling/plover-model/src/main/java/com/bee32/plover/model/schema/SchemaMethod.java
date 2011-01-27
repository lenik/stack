package com.bee32.plover.model.schema;

import com.bee32.plover.model.stereo.StereoType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchemaMethod
        extends SchemaElement {

    public SchemaMethod(String name, Class type) {
        super(name, StereoType.COMMAND, type);
    }

}
