package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.HashSet;

import javax.free.Nullables;

import com.bee32.plover.arch.Component;
import com.bee32.plover.orm.entity.IEntity;

public class RoleBean
        extends AbstractRole
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Integer id;

    protected IRolePrincipal inheritedRole;

    protected Collection<IUserPrincipal> responsibleUsers;
    protected Collection<IGroupPrincipal> responsibleGroups;

    public RoleBean() {
        super();
    }

    public RoleBean(String name) {
        super(name);
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
        if (responsibleUsers == null) {
            synchronized (this) {
                if (responsibleUsers == null) {
                    responsibleUsers = new HashSet<IUserPrincipal>();
                }
            }
        }
        return responsibleUsers;
    }

    public void setResponsibleUsers(Collection<IUserPrincipal> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public Collection<IGroupPrincipal> getResponsibleGroups() {
        if (responsibleGroups == null) {
            synchronized (this) {
                if (responsibleGroups == null) {
                    responsibleGroups = new HashSet<IGroupPrincipal>();
                }
            }
        }
        return responsibleGroups;
    }

    public void setResponsibleGroups(Collection<IGroupPrincipal> responsibleGroups) {
        this.responsibleGroups = responsibleGroups;
    }

    @Override
    protected int hashCodeSpecific() {
        final int prime = 31;
        if (id != null)
            return prime * id.hashCode();

        int result = 0;
        result = prime * result + ((inheritedRole == null) ? 0 : inheritedRole.hashCode());
        result = prime * result + ((responsibleUsers == null) ? 0 : responsibleUsers.hashCode());
        result = prime * result + ((responsibleGroups == null) ? 0 : responsibleGroups.hashCode());

        return result;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
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
