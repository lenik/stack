package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.bee32.icsf.principal.AbstractGroup;
import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;

public class SimpleGroup
        extends AbstractGroup {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final IGroupPrincipal parent;
    private final List<IRolePrincipal> roles;
    private final List<IUserPrincipal> users;

    public SimpleGroup(String name, IGroupPrincipal parent) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.parent = parent;
        this.roles = new ArrayList<IRolePrincipal>(4);
        this.users = new ArrayList<IUserPrincipal>();
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
    public Collection<? extends IRolePrincipal> getAssignedRoles() {
        return Collections.unmodifiableList(roles);
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
    public Collection<? extends IUserPrincipal> getMemberUsers() {
        return Collections.unmodifiableList(users);
    }

    void addMemberUser(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        users.add(user);
    }

    void removeMemberUser(IUserPrincipal user) {
        users.remove(user);
    }

}
