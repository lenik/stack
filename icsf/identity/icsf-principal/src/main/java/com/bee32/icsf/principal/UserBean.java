package com.bee32.icsf.principal;

import java.util.Collection;

import javax.free.Nullables;

import com.bee32.plover.orm.entity.IEntity;

public class UserBean
        extends AbstractUser
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Long id;

    protected IGroupPrincipal primaryGroup;
    protected IRolePrincipal primaryRole;

    protected Collection<IGroupPrincipal> assignedGroups;
    protected Collection<IRolePrincipal> assignedRoles;

    @Override
    public Long getPrimaryKey() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Override
    public IGroupPrincipal getPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(IGroupPrincipal primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    @Override
    public IRolePrincipal getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(IRolePrincipal primaryRole) {
        this.primaryRole = primaryRole;
    }

    @Override
    public Collection<IGroupPrincipal> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(Collection<IGroupPrincipal> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }

    @Override
    public Collection<IRolePrincipal> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(Collection<IRolePrincipal> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        if (this.equals(principal))
            return true;

        if (primaryGroup != null)
            if (primaryGroup.implies(principal))
                return true;

        if (primaryRole != null)
            if (primaryRole.implies(principal))
                return true;

        for (IGroupPrincipal group : getAssignedGroups())
            if (group.implies(principal))
                return true;

        for (IRolePrincipal role : getAssignedRoles())
            if (role.implies(principal))
                return true;

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = (name == null) ? 0 : name.hashCode();
        result = prime * result + ((primaryRole == null) ? 0 : primaryRole.hashCode());
        result = prime * result + ((primaryGroup == null) ? 0 : primaryGroup.hashCode());
        result = prime * result + ((assignedRoles == null) ? 0 : assignedRoles.hashCode());
        result = prime * result + ((assignedGroups == null) ? 0 : assignedGroups.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserBean other = (UserBean) obj;

        if (id != null)
            return !id.equals(other.id);

        if (!Nullables.equals(primaryGroup, other.primaryGroup))
            return false;
        if (!Nullables.equals(primaryRole, other.primaryRole))
            return false;
        if (!Nullables.equals(assignedGroups, other.assignedGroups))
            return false;
        if (!Nullables.equals(assignedRoles, other.assignedRoles))
            return false;

        return true;
    }

}
