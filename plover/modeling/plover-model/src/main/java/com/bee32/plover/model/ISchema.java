package com.bee32.plover.model;

public interface ISchema {

    Iterable<String> listNames();

    Iterable<ISchemaElement<?>> list();

    ISchemaElement<?> getElement(String name);

}
