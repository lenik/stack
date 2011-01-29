package com.bee32.plover.model.stage;

import com.bee32.plover.model.schema.ISchema;

public abstract class StagedElement
        extends VirtualStagedElement
        implements IStagedElement {

    @Override
    public Class<? extends ISchema<?>> getElementSchema() {
        return null;
    }

    @Override
    public Class<?> getElementType() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object obj) {
    }

    @Override
    public boolean isDirty() {
        return false;
    }

}
