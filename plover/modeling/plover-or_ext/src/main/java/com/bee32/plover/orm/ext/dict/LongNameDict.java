package com.bee32.plover.orm.ext.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = 50)),
/*    */@AttributeOverride(name = "label", column = @Column(length = 100)) })
public abstract class LongNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public LongNameDict() {
        super();
    }

    public LongNameDict(String name, String label) {
        super(name, label);
    }

    public LongNameDict(String name, String label, String description) {
        super(name, label, description);
    }

    public LongNameDict(int order, String name, String label) {
        super(order, name, label);
    }

    public LongNameDict(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

}
