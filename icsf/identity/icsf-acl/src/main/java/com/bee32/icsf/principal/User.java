package com.bee32.icsf.principal;

import java.util.HashSet;
import java.util.Set;

import javax.free.Nullables;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@DiscriminatorValue("U")
public class User
        extends AbstractUser
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Group primaryGroup;
    protected Role primaryRole;

    protected Set<Group> assignedGroups;
    protected Set<Role> assignedRoles;

    public User() {
    }

    public User(String name) {
        super(name);
    }

    public User(String name, Group primaryGroup, Role primaryRole) {
        super(name);
        this.primaryGroup = primaryGroup;
        this.primaryRole = primaryRole;
    }

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "group1")
    @Override
    public Group getPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(Group primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role1")
    @Override
    public Role getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(Role primaryRole) {
        this.primaryRole = primaryRole;
    }

    @ManyToMany(targetEntity = Group.class, mappedBy = "memberUsers")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Set<Group> getAssignedGroups() {
        if (assignedGroups == null) {
            synchronized (this) {
                if (assignedGroups == null) {
                    assignedGroups = new HashSet<Group>();
                }
            }
        }
        return assignedGroups;
    }

    public void setAssignedGroups(Set<Group> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }

    @ManyToMany(targetEntity = Role.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "UserRole", //
    /*            */joinColumns = @JoinColumn(name = "user"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "role"))
    @Override
    public Set<Role> getAssignedRoles() {
        if (assignedRoles == null) {
            synchronized (this) {
                if (assignedRoles == null) {
                    assignedRoles = new HashSet<Role>();
                }
            }
        }
        return assignedRoles;
    }

    public void setAssignedRoles(Set<Role> assignedRoles) {
        this.assignedRoles = (Set<Role>) assignedRoles;
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
    protected int hashCodeEntity() {
        if (!PrincipalBeanConfig.fullEquality)
            return super.hashCodeEntity();

        final int prime = 31;

        int result = (name == null) ? 0 : name.hashCode();
        result = prime * result + ((primaryRole == null) ? 0 : primaryRole.hashCode());
        result = prime * result + ((primaryGroup == null) ? 0 : primaryGroup.hashCode());
        result = prime * result + ((assignedRoles == null) ? 0 : assignedRoles.hashCode());
        result = prime * result + ((assignedGroups == null) ? 0 : assignedGroups.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> obj) {
        if (!PrincipalBeanConfig.fullEquality)
            return false;

        User other = (User) obj;

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
