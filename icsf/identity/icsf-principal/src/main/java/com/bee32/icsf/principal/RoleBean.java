package com.bee32.icsf.principal;

import java.util.Collection;

import javax.free.Nullables;

import com.bee32.plover.orm.entity.IEntity;

public class RoleBean
        extends AbstractRole
        implements IEntity<Integer> {

    private static final long serialVersionUID = 1L;

    protected Integer id;

    protected IRolePrincipal inheritedRole;

    protected Collection<IUserPrincipal> responsibleUsers;
    protected Collection<IGroupPrincipal> responsibleGroups;

    @Override
    public Integer getPrimaryKey() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    public IRolePrincipal getInheritedRole() {
        return inheritedRole;
    }

    public void setInheritedRole(IRolePrincipal inheritedRole) {
        this.inheritedRole = inheritedRole;
    }

    public Collection<IUserPrincipal> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(Collection<IUserPrincipal> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public Collection<IGroupPrincipal> getResponsibleGroups() {
        return responsibleGroups;
    }

    public void setResponsibleGroups(Collection<IGroupPrincipal> responsibleGroups) {
        this.responsibleGroups = responsibleGroups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = super.hashCode();
        result = prime * result + ((inheritedRole == null) ? 0 : inheritedRole.hashCode());
        result = prime * result + ((responsibleUsers == null) ? 0 : responsibleUsers.hashCode());
        result = prime * result + ((responsibleGroups == null) ? 0 : responsibleGroups.hashCode());

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
        RoleBean other = (RoleBean) obj;

        if (id != null)
            return !id.equals(other.id);

        if (!Nullables.equals(inheritedRole, other.inheritedRole))
            return false;
        if (!Nullables.equals(responsibleUsers, other.responsibleUsers))
            return false;
        if (!Nullables.equals(responsibleGroups, other.responsibleGroups))
            return false;

        return true;
    }

}
