package com.bee32.plover.model;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchemaProperty
        extends SchemaElement {

    public SchemaProperty(String name, Class propertyType) {
        super(name, SchemaElementStereoType.PROPERTY, propertyType);
    }

}
