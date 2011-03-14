package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.HashSet;

import javax.free.Nullables;
import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
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

    @Override
    protected int hashCodeEntity() {
        if (!PrincipalBeanConfig.fullEquality)
            return super.hashCodeEntity();

        return super.hashCodeEntity();
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        if (!PrincipalBeanConfig.fullEquality)
            return false;

        GroupBean other = (GroupBean) otherEntity;

        if (!Nullables.equals(inheritedGroup, other.inheritedGroup))
            return false;

        if (!Nullables.equals(owner, other.owner))
            return false;

        if (!Nullables.equals(primaryRole, other.primaryRole))
            return false;

        if (!Nullables.equals(assignedRoles, other.assignedRoles))
            return false;

        if (!Nullables.equals(memberUsers, other.memberUsers))
            return false;

        return true;
    }

}
