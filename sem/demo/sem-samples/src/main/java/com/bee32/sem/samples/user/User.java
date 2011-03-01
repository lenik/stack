package com.bee32.sem.samples.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.bee32.icsf.principal.AbstractUser;
import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;

public class User
        extends AbstractUser {

    private static final long serialVersionUID = 1L;

    private final String name;

    private UserGroup dept;

    private Role role;

    public User(String name, UserGroup dept, Role role) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.dept = dept;
        this.role = role;
    }

    @Override
    public String getName() {
        return name;
    }

    public UserGroup getDept() {
        return dept;
    }

    @Override
    public Collection<? extends IGroupPrincipal> getAssignedGroups() {
        if (dept == null)
            return Collections.emptyList();
        ArrayList<UserGroup> group = new ArrayList<UserGroup>();
        group.add(dept);
        return group;
    }

    @Override
    public Collection<? extends IRolePrincipal> getAssignedRoles() {
        return Collections.emptyList();
    }

    public void setDept(UserGroup dept) {
        this.dept = dept;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person " + name + " works in " + dept + " plays a " + role + " role. ";
    }

}
