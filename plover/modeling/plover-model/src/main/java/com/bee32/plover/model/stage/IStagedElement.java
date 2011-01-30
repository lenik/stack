package com.bee32.plover.model.stage;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;
import com.bee32.plover.model.schema.ISchema;

public interface IStagedElement
        extends IComponent, IQualified, Iterable<IStagedElement> {

    /**
     * Get the schema of the element, if there is any.
     *
     * @return The schema instance, maybe created on the fly. If the element is virtual,
     *         <code>null</code> is returned.
     */
    ISchema getSchema();

    /**
     * Get the type of the object this element refers to.
     *
     * @return <code>null</code> if not applicable.
     */
    Class<?> getType();

    /**
     * Get the staged value.
     *
     * @return Staged value.
     */
    Object getValue();

    /**
     * Change the staged value.
     *
     * You should not change the value of virtual element.
     *
     * @param obj
     *            New value to change.
     */
    void setValue(Object obj);

    /**
     * If the new value is not {@link #equals(Object) equals} the old, then it's dirty. However, a
     * dirty value may equals to the old.
     *
     * @return <code>true</code> If value of the element has been turned to dirty.
     */
    boolean isDirty();

}
