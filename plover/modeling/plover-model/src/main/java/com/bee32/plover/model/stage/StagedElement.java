package com.bee32.plover.model.stage;

public class StagedElement
        extends StagedNode
        implements IStagedElement {

    protected Object value;
    private boolean dirty;

    public StagedElement() {
        super();
    }

    public StagedElement(String name) {
        super(name);
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object obj) {
        this.value = obj;
        dirty = true;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

}
