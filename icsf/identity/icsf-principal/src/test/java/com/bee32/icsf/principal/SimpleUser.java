package com.bee32.icsf.principal;

import javax.persistence.Entity;

@Entity
public class SimpleUser
        extends User {

    private static final long serialVersionUID = 1L;

    public SimpleUser() {
        super();
    }

    public SimpleUser(String name, SimpleGroup primaryGroup, SimpleRole primaryRole) {
        super(name, primaryGroup, primaryRole);
    }

    public SimpleUser(String name) {
        super(name);
    }

}
