package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.HashSet;

import com.bee32.plover.orm.entity.IEntity;

public class GroupBean
        extends AbstractGroup
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected IGroupPrincipal inheritedGroup;
    protected IUserPrincipal owner;
    protected IRolePrincipal primaryRole;

    protected Collection<IRolePrincipal> assignedRoles;
    protected Collection<IUserPrincipal> memberUsers;

    public GroupBean() {
    }

    public GroupBean(String name) {
        super(name);
    }

    public GroupBean(String name, IUserPrincipal owner, IUserPrincipal... memberUsers) {
        super(name);
        this.owner = owner;

        addMemberUser(owner);

        for (IUserPrincipal user : memberUsers)
            addMemberUser(user);
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    public void setName(String name, IUserPrincipal owner) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Override
    public IGroupPrincipal getInheritedGroup() {
        return inheritedGroup;
    }

    public void setInheritedGroup(IGroupPrincipal inheritedGroup) {
        this.inheritedGroup = inheritedGroup;
    }

    @Override
    public IUserPrincipal getOwner() {
        return owner;
    }

    public void setOwner(IUserPrincipal owner) {
        this.owner = owner;
    }

    @Override
    public IRolePrincipal getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(IRolePrincipal primaryRole) {
        this.primaryRole = primaryRole;
    }

    @Override
    public Collection<IRolePrincipal> getAssignedRoles() {
        if (assignedRoles == null) {
            synchronized (this) {
                if (assignedRoles == null) {
                    assignedRoles = new HashSet<IRolePrincipal>();
                }
            }
        }
        return assignedRoles;
    }

    public void setAssignedRoles(Collection<? extends IRolePrincipal> assignedRoles) {
        this.assignedRoles = new HashSet<IRolePrincipal>(assignedRoles);
    }

    @Override
    public Collection<IUserPrincipal> getMemberUsers() {
        if (memberUsers == null) {
            synchronized (this) {
                if (memberUsers == null) {
                    memberUsers = new HashSet<IUserPrincipal>();
                }
            }
        }
        return memberUsers;
    }

    public void setMemberUsers(Collection<? extends IUserPrincipal> memberUsers) {
        this.memberUsers = new HashSet<IUserPrincipal>(memberUsers);
    }

}
