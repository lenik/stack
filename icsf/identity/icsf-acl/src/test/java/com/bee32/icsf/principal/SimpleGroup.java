package com.bee32.icsf.principal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("G1")
public class SimpleGroup
        extends Group {

    private static final long serialVersionUID = 1L;

    public SimpleGroup() {
        super();
    }

    public SimpleGroup(String name, SimpleUser owner, SimpleUser... memberUsers) {
        super(name, owner, memberUsers);
    }

    public SimpleGroup(String name) {
        super(name);
    }

}
