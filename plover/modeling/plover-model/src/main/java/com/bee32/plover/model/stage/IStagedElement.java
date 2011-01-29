package com.bee32.plover.model.stage;

import com.bee32.plover.model.schema.ISchema;

public interface IStagedElement
        extends IVirtualStagedElement {

    Class<? extends ISchema<?>> getElementSchema();

    Class<?> getElementType();

    Object getValue();

    void setValue(Object obj);

    /**
     * If the new value is not {@link #equals(Object) equals} the old, then it's dirty. However, a
     * dirty value may equals to the old.
     *
     * @return <code>true</code> If value of the element has been turned to dirty.
     */
    boolean isDirty();

}
