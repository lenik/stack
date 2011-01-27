package com.bee32.plover.model;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchemaMethod
        extends SchemaElement {

    public SchemaMethod(String name, Class type) {
        super(name, SchemaElementStereoType.METHOD, type);
    }

}
