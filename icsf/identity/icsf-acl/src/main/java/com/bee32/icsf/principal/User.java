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

import com.bee32.icsf.access.alt.R_ACL;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@DiscriminatorValue("U")
public class User
        extends AbstractUser
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected IGroupPrincipal primaryGroup;
    protected IRolePrincipal primaryRole;

    protected Set<? extends IGroupPrincipal> assignedGroups;
    protected Set<? extends IRolePrincipal> assignedRoles;

    public User() {
    }

    public User(String name) {
        super(name);
    }

    public User(String name, IGroupPrincipal primaryGroup, IRolePrincipal primaryRole) {
        super(name);
        this.primaryGroup = primaryGroup;
        this.primaryRole = primaryRole;
    }

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "group1")
    @Override
    public IGroupPrincipal getPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(IGroupPrincipal primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role1")
    @Override
    public IRolePrincipal getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(IRolePrincipal primaryRole) {
        this.primaryRole = primaryRole;
    }

    @SuppressWarnings("unchecked")
    @ManyToMany(targetEntity = Group.class, mappedBy = "memberUsers")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public Set<IGroupPrincipal> getAssignedGroups() {
        if (assignedGroups == null) {
            synchronized (this) {
                if (assignedGroups == null) {
                    assignedGroups = new HashSet<IGroupPrincipal>();
                }
            }
        }
        return (Set<IGroupPrincipal>) assignedGroups;
    }

    public void setAssignedGroups(Set<? extends IGroupPrincipal> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }

    @SuppressWarnings("unchecked")
    @ManyToMany(targetEntity = Role.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "UserRole", //
    /*            */joinColumns = @JoinColumn(name = "user"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "role"))
    @Override
    public Set<IRolePrincipal> getAssignedRoles() {
        if (assignedRoles == null) {
            synchronized (this) {
                if (assignedRoles == null) {
                    assignedRoles = new HashSet<IRolePrincipal>();
                }
            }
        }
        return (Set<IRolePrincipal>) assignedRoles;
    }

    public void setAssignedRoles(Set<? extends IRolePrincipal> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    private R_ACL systemAcl;

    @ManyToMany()
    @JoinTable(name = "Permissions", //
    /*            */joinColumns = @JoinColumn(name = "person"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "permissionInst"))
    public R_ACL getSystemAcl() {
        return systemAcl;
    }

    public void setSystemAcl(R_ACL systemAcl) {
        this.systemAcl = systemAcl;
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
