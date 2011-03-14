package com.bee32.icsf.principal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.Nullables;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@Table(name = "Role")
@DiscriminatorValue("role")
public class Role
        extends AbstractRole
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected IRolePrincipal inheritedRole;

    protected Set<IUserPrincipal> responsibleUsers;
    protected Set<IGroupPrincipal> responsibleGroups;

    public Role() {
        super();
    }

    public Role(String name) {
        super(name);
    }

    @Override
    @JoinColumn(name = "parent")
    public IRolePrincipal getInheritedRole() {
        return inheritedRole;
    }

    public void setInheritedRole(IRolePrincipal inheritedRole) {
        this.inheritedRole = inheritedRole;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    /*            */targetEntity = User.class, mappedBy = "assignedRoles")
    public Set<IUserPrincipal> getResponsibleUsers() {
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
        this.responsibleUsers = new HashSet<IUserPrincipal>(responsibleUsers);
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    /*            */targetEntity = Group.class, mappedBy = "assignedRoles")
    public Set<IGroupPrincipal> getResponsibleGroups() {
        if (responsibleGroups == null) {
            synchronized (this) {
                if (responsibleGroups == null) {
                    responsibleGroups = new HashSet<IGroupPrincipal>();
                }
            }
        }
        return responsibleGroups;
    }

    public void setResponsibleGroups(Collection<? extends IGroupPrincipal> responsibleGroups) {
        this.responsibleGroups = new HashSet<IGroupPrincipal>(responsibleGroups);
    }

    @Override
    protected int hashCodeEntity() {
        if (!PrincipalBeanConfig.fullEquality)
            return super.hashCodeEntity();

        final int prime = 31;

        int result = 0;
        result = prime * result + ((inheritedRole == null) ? 0 : inheritedRole.hashCode());
        result = prime * result + ((responsibleUsers == null) ? 0 : responsibleUsers.hashCode());
        result = prime * result + ((responsibleGroups == null) ? 0 : responsibleGroups.hashCode());

        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> entity) {
        if (!PrincipalBeanConfig.fullEquality)
            return false;

        Role other = (Role) entity;

        if (!Nullables.equals(inheritedRole, other.inheritedRole))
            return false;

        if (!Nullables.equals(responsibleUsers, other.responsibleUsers))
            return false;

        if (!Nullables.equals(responsibleGroups, other.responsibleGroups))
            return false;

        return true;
    }

}
