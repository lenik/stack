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

@Entity
@DiscriminatorValue("R")
public class Role
        extends AbstractRole {

    private static final long serialVersionUID = 1L;

    Role inheritedRole;

    Set<Role> derivedRoles;
    Set<User> responsibleUsers;
    Set<Group> responsibleGroups;

    public Role() {
        super();
    }

    public Role(String name) {
        super(name);
    }

    @ManyToOne(targetEntity = Role.class, optional = true)
    @JoinColumn(name = "parent")
    @Override
    public Role getInheritedRole() {
        return inheritedRole;
    }

    public void setInheritedRole(IRolePrincipal inheritedRole) {
        this.inheritedRole = cast(inheritedRole);
    }

    @Transient
    @OneToMany(targetEntity = Role.class, mappedBy = "inheritedRole")
    @Override
    public Set<Role> getDerivedRoles() {
        if (derivedRoles == null)
            return null;
        else
            return Collections.unmodifiableSet(derivedRoles);
    }

    public void setDerivedRoles(Set<Role> derivedRoles) {
        this.derivedRoles = derivedRoles;
    }

    @ManyToMany(targetEntity = User.class, mappedBy = "assignedRoles")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Set<User> getResponsibleUsers() {
        if (responsibleUsers == null) {
            synchronized (this) {
                if (responsibleUsers == null) {
                    responsibleUsers = new HashSet<User>();
                }
            }
        }
        return responsibleUsers;
    }

    public void setResponsibleUsers(Set<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    @ManyToMany(targetEntity = Group.class, mappedBy = "assignedRoles")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Set<Group> getResponsibleGroups() {
        if (responsibleGroups == null) {
            synchronized (this) {
                if (responsibleGroups == null) {
                    responsibleGroups = new HashSet<Group>();
                }
            }
        }
        return responsibleGroups;
    }

    public void setResponsibleGroups(Set<Group> responsibleGroups) {
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
