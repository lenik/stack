package com.bee32.plover.model;


public class AnnotationSchemaBuilder
        extends SchemaBuilder {

    @Override
    public int getPriority() {
        return -10;
    }

    @Override
    public ISchema buildSchema(Class<?> type)
            throws SchemaBuilderException {
        Schema schemaAnnotation = type.getAnnotation(Schema.class);
        Class<? extends ISchema> schemaClass = schemaAnnotation.value();

        ISchema schema;
        try {
            schema = schemaClass.newInstance();
        } catch (Exception e) {
            throw new SchemaBuilderException(e.getMessage(), e);
        }

        return schema;
    }

}
