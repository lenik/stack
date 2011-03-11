package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.Collections;

@Deprecated
public class SimpleRole
        extends AbstractRole {

    private static final long serialVersionUID = 1L;

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

    @Override
    public Collection<IUserPrincipal> getResponsibleUsers() {
        return Collections.emptyList();
    }

    @Override
    public Collection<IGroupPrincipal> getResponsibleGroups() {
        return Collections.emptyList();
    }

}
