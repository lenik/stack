package com.bee32.plover.model.stage;

import com.bee32.plover.model.schema.ISchema;

public class StageBuilder {

    /**
     * Non-<code>null</code> enclosing instance.
     */
    private final Object enclosing;

    /**
     * The target stage.
     */
    private final IModelStage stage;

    /**
     * The loaded schema for the enclosing object.
     */
    private ISchema<?> schema;

    public StageBuilder(Object enclosing, IModelStage stage) {
        if (enclosing == null)
            throw new NullPointerException("enclosing");
        if (stage == null)
            throw new NullPointerException("stage");
        this.enclosing = enclosing;
        this.stage = stage;
        // getClass().getEnclosingClass();
    }

    public void field(String propertyName, Object value) {
        FieldElement fieldElement = null;
        stage.add(fieldElement);
    }

    public void property(String propertyName, Object value) {
        PropertyElement propertyElement = null;
        stage.add(propertyElement);
    }

    public void method(String methodName, ParameterElement... parameters) {
        MethodElement methodElement = null;
        stage.add(methodElement);
    }

    public void group(String groupName, StagedElement... elements) {

    }

    public void doc(String docName) {

    }

}
