package com.bee32.icsf.principal;

import java.util.Collection;

import javax.free.Nullables;

import com.bee32.plover.arch.Component;
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
        return assignedRoles;
    }

    public void setAssignedRoles(Collection<IRolePrincipal> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    @Override
    public Collection<IUserPrincipal> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(Collection<IUserPrincipal> memberUsers) {
        this.memberUsers = memberUsers;
    }

    @Override
    protected int hashCodeSpecific() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = 0;
        result = prime * result + ((assignedRoles == null) ? 0 : assignedRoles.hashCode());
        result = prime * result + ((inheritedGroup == null) ? 0 : inheritedGroup.hashCode());
        result = prime * result + ((memberUsers == null) ? 0 : memberUsers.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((primaryRole == null) ? 0 : primaryRole.hashCode());

        return result;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        GroupBean other = (GroupBean) obj;

        if (id != null)
            return !id.equals(other.id);

        if (!Nullables.equals(owner, other.owner))
            return false;
        if (!Nullables.equals(primaryRole, other.primaryRole))
            return false;
        if (!Nullables.equals(inheritedGroup, other.inheritedGroup))
            return false;
        if (!Nullables.equals(assignedRoles, other.assignedRoles))
            return false;
        if (!Nullables.equals(memberUsers, other.memberUsers))
            return false;

        return true;
    }

}
