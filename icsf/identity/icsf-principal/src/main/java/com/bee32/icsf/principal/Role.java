package com.bee32.icsf.principal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.free.Nullables;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@DiscriminatorValue("R")
public class Role
        extends AbstractRole
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected IRolePrincipal inheritedRole;

    protected Set<? extends IRolePrincipal> derivedRoles;

    protected Set<? extends IUserPrincipal> responsibleUsers;
    protected Set<? extends IGroupPrincipal> responsibleGroups;

    public Role() {
        super();
    }

    public Role(String name) {
        super(name);
    }

    @ManyToOne(targetEntity = Role.class, optional = true)
    @JoinColumn(name = "parent")
    @Override
    public IRolePrincipal getInheritedRole() {
        return inheritedRole;
    }

    public void setInheritedRole(IRolePrincipal inheritedRole) {
        this.inheritedRole = inheritedRole;
    }

    @Transient
    @OneToMany(targetEntity = Role.class, mappedBy = "inheritedRole")
    @Override
    public Set<? extends IRolePrincipal> getDerivedRoles() {
        if (derivedRoles == null)
            return null;
        else
            return Collections.unmodifiableSet(derivedRoles);
    }

    public void setDerivedRoles(Set<? extends IRolePrincipal> derivedRoles) {
        this.derivedRoles = derivedRoles;
    }

    @SuppressWarnings("unchecked")
    @ManyToMany(targetEntity = User.class, mappedBy = "assignedRoles")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Set<IUserPrincipal> getResponsibleUsers() {
        if (responsibleUsers == null) {
            synchronized (this) {
                if (responsibleUsers == null) {
                    responsibleUsers = new HashSet<IUserPrincipal>();
                }
            }
        }
        return (Set<IUserPrincipal>) responsibleUsers;
    }

    public void setResponsibleUsers(Set<? extends IUserPrincipal> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    @SuppressWarnings("unchecked")
    @ManyToMany(targetEntity = Group.class, mappedBy = "assignedRoles")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Set<IGroupPrincipal> getResponsibleGroups() {
        if (responsibleGroups == null) {
            synchronized (this) {
                if (responsibleGroups == null) {
                    responsibleGroups = new HashSet<IGroupPrincipal>();
                }
            }
        }
        return (Set<IGroupPrincipal>) responsibleGroups;
    }

    public void setResponsibleGroups(Set<? extends IGroupPrincipal> responsibleGroups) {
        this.responsibleGroups = responsibleGroups;
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
