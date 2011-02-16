package com.bee32.icsf.principal;

import com.bee32.icsf.principal.AbstractRole;
import com.bee32.icsf.principal.IRolePrincipal;


public class SimpleRole
        extends AbstractRole {

    private final String name;
    private final IRolePrincipal parent;

    public SimpleRole(String name, IRolePrincipal parent) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IRolePrincipal getInheritedRole() {
        return parent;
    }

}
