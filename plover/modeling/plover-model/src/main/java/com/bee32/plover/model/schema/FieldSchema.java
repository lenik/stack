package com.bee32.plover.model.schema;

import java.lang.reflect.Field;
import java.util.Map;

import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.stereo.StereoType;

public class FieldSchema
        extends AbstractSchema {

    private final Field field;

    public FieldSchema(Field field) {
        super(field.getName(), StereoType.PROPERTY, field.getType());
        this.field = field;
    }

    @Override
    protected QualifierMap loadQualifierMap() {
        QualifierMap qualifierMap = new QualifierMap();

        // for (Annotation annotation : field.getAnnotations()) {
        // if (annotation instanceof )
        // }
        return qualifierMap;
    }

    @Override
    protected void loadChildren(Map<SchemaKey, ISchema> map) {
        // Field has no children.
    }

    public Field getField() {
        return field;
    }

}
