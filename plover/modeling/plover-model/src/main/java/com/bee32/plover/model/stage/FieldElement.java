package com.bee32.plover.model.stage;

import java.lang.reflect.Field;

import com.bee32.plover.model.schema.ISchema;

public class FieldElement
        extends StagedElement {

    private final Field field;

    public FieldElement(Field field, Object value) {
        super(field.getName());
        this.field = field;
        this.value = value;
    }

    @Override
    protected ISchema buildSchema() {
        return null;
    }

    public Field getField() {
        return field;
    }

}
