package com.bee32.icsf.principal;

import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R1")
public class SimpleRole
        extends Role {

    private static final long serialVersionUID = 1L;

    String name;
    Role parent;

    public SimpleRole(String name, Role parent) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.parent = parent;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof SimpleRole)
            _populate((SimpleRole) source);
        else
            super.populate(source);
    }

    protected void _populate(SimpleRole o) {
        super._populate(o);
        name = o.name;
        parent = o.parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Role getInheritedRole() {
        return parent;
    }

    @Override
    public List<Role> getDerivedRoles() {
        return Collections.emptyList();
    }

    @Override
    public List<User> getResponsibleUsers() {
        return Collections.emptyList();
    }

    @Override
    public List<Group> getResponsibleGroups() {
        return Collections.emptyList();
    }

}
