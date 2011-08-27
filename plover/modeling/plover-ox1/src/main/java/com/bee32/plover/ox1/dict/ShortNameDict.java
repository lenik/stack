package com.bee32.plover.ox1.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = 10)),
/*    */@AttributeOverride(name = "label", column = @Column(length = 30)) })
public abstract class ShortNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public ShortNameDict() {
        super();
    }

    public ShortNameDict(String name, String label) {
        super(name, label);
    }

    public ShortNameDict(String name, String label, String description) {
        super(name, label, description);
    }

    public ShortNameDict(int order, String name, String label) {
        super(order, name, label);
    }

    public ShortNameDict(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

}
