package com.bee32.icsf.principal;

import java.util.Collections;
import java.util.Set;

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
    public Set<IUserPrincipal> getResponsibleUsers() {
        return Collections.emptySet();
    }

    @Override
    public Set<IGroupPrincipal> getResponsibleGroups() {
        return Collections.emptySet();
    }

}
