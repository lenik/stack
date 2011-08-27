package com.bee32.icsf.principal;

import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.Role;
import com.bee32.plover.ox1.principal.User;

@Entity
@DiscriminatorValue("R1")
public class SimpleRole
        extends Role {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final Role parent;

    public SimpleRole(String name, Role parent) {
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
