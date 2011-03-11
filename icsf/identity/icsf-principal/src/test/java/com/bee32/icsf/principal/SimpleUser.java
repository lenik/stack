package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Deprecated
public class SimpleUser
        extends AbstractUser {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final List<IGroupPrincipal> groups;
    private final List<IRolePrincipal> roles;

    public SimpleUser(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.groups = new ArrayList<IGroupPrincipal>(1);
        this.roles = new ArrayList<IRolePrincipal>(1);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPrimaryRole(IRolePrincipal role) {
    }

    @Override
    public void setPrimaryGroup(IGroupPrincipal group) {
    }

    @Override
    public Collection<IGroupPrincipal> getAssignedGroups() {
        return Collections.unmodifiableList(groups);
    }

    @Override
    public Collection<IRolePrincipal> getAssignedRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void assignGroup(SimpleGroup group) {
        if (group == null)
            throw new NullPointerException("group");
        groups.add(group);
        group.addMemberUser(this);
    }

    public void unassignGroup(SimpleGroup group) {
        groups.remove(group);
        group.removeMemberUser(this);
    }

    public void assignRole(IRolePrincipal role) {
        if (role == null)
            throw new NullPointerException("role");
        roles.add(role);
    }

    public void unassignRole(IRolePrincipal role) {
        roles.remove(role);
    }

}
