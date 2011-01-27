package com.bee32.plover.model.schema;

public abstract class SchemaBuilder {

    public abstract int getPriority();

    public abstract ISchema buildSchema(Class<?> type)
            throws SchemaBuilderException;

}
