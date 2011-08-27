package com.bee32.plover.ox1.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = 32)),
/*    */@AttributeOverride(name = "label", column = @Column(length = 50)) })
public abstract class GUIDNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public GUIDNameDict() {
        super();
    }

    public GUIDNameDict(String name, String label) {
        super(name, label);
    }

    public GUIDNameDict(String name, String label, String description) {
        super(name, label, description);
    }

    protected void initGUID() {

    }

}
