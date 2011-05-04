package com.bee32.plover.orm.ext.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "name", column = @Column(length = 10)),
/*    */@AttributeOverride(name = "alias", column = @Column(length = 30)) })
public abstract class ShortNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public ShortNameDict() {
        super();
    }

    public ShortNameDict(String name, String alias) {
        super(name, alias);
    }

    public ShortNameDict(String name, String alias, String description) {
        super(name, alias, description);
    }

}
