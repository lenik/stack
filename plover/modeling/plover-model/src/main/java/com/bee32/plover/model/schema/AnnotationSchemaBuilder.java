package com.bee32.plover.model.schema;

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

        try {
            ISchema schema = (ISchema) schemaClass.newInstance();
            return schema;
        } catch (ClassCastException e) {
            throw new SchemaBuilderException("Bad schema type annotated", e);
        } catch (Exception e) {
            throw new SchemaBuilderException(e.getMessage(), e);
        }
    }

}
