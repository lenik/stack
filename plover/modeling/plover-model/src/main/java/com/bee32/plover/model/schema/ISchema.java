package com.bee32.plover.model.schema;

public interface ISchema {

    Iterable<String> listNames();

    Iterable<ISchemaElement<?>> list();

    ISchemaElement<?> getElement(String name);

}
