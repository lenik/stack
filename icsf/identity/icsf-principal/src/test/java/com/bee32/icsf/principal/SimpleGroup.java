package com.bee32.icsf.principal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class SimpleGroup
        extends AbstractGroup {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final IGroupPrincipal parent;
    private final Set<IRolePrincipal> roles;
    private final Set<IUserPrincipal> users;

    public SimpleGroup(String name, IGroupPrincipal parent) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.parent = parent;
        this.roles = new HashSet<IRolePrincipal>(4);
        this.users = new HashSet<IUserPrincipal>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IGroupPrincipal getInheritedGroup() {
        return parent;
    }

    @Override
    public IUserPrincipal getOwner() {
        return null;
    }

    @Override
    public void setOwner(IUserPrincipal owner) {
    }

    @Override
    public void setPrimaryRole(IRolePrincipal role) {
    }

    @Override
    public Set<IRolePrincipal> getAssignedRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void assignRole(IRolePrincipal role) {
        if (role == null)
            throw new NullPointerException("role");
        roles.add(role);
    }

    public void unassignRole(IRolePrincipal role) {
        roles.remove(role);
    }

    @Override
    public Set<IUserPrincipal> getMemberUsers() {
        return Collections.unmodifiableSet(users);
    }

    public void addMemberUser(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        users.add(user);
    }

    public void removeMemberUser(IUserPrincipal user) {
        users.remove(user);
    }

}
