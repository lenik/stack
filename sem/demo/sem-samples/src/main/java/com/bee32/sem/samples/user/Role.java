package com.bee32.sem.samples.user;

import com.bee32.icsf.principal.AbstractRole;
import com.bee32.icsf.principal.IRolePrincipal;

public class Role
        extends AbstractRole {

    private static final long serialVersionUID = 1L;

    private final String name;

    public Role(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public IRolePrincipal getInheritedRole() {
        return null;
    }

}
