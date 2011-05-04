package com.bee32.plover.orm.ext.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "name", column = @Column(length = 30)),
/*    */@AttributeOverride(name = "alias", column = @Column(length = 30)) })
public abstract class LongNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public LongNameDict() {
        super();
    }

    public LongNameDict(String name, String alias) {
        super(name, alias);
    }

    public LongNameDict(String name, String alias, String description) {
        super(name, alias, description);
    }

}
