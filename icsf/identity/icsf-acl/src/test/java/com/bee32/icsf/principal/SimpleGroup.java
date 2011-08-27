package com.bee32.icsf.principal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.User;

@Entity
@DiscriminatorValue("G1")
public class SimpleGroup
        extends Group {

    private static final long serialVersionUID = 1L;

    public SimpleGroup() {
        super();
    }

    public SimpleGroup(String name) {
        super(name);
    }

    public SimpleGroup(String name, String fullName) {
        super(name, fullName);
    }

    public SimpleGroup(String name, String fullName, User owner, User... memberUsers) {
        super(name, fullName, owner, memberUsers);
    }

}
