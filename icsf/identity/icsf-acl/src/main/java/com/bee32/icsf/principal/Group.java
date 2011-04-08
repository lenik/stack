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
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@DiscriminatorValue("G")
public class Group
        extends AbstractGroup
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected IGroupPrincipal inheritedGroup;
    protected IUserPrincipal owner;
    protected IRolePrincipal primaryRole;

    protected Set<? extends IGroupPrincipal> derivedGroups;

    protected Set<? extends IRolePrincipal> assignedRoles;
    protected Set<? extends IUserPrincipal> memberUsers;

    public Group() {
    }

    public Group(String name) {
        super(name);
    }

    public Group(String name, IUserPrincipal owner, IUserPrincipal... memberUsers) {
        super(name);
        this.owner = owner;

        addMemberUser(owner);

        for (IUserPrincipal user : memberUsers)
            addMemberUser(user);
    }

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "parent")
    @Override
    public IGroupPrincipal getInheritedGroup() {
        return inheritedGroup;
    }

    public void setInheritedGroup(IGroupPrincipal inheritedGroup) {
        this.inheritedGroup = cast(inheritedGroup);
    }

    @SuppressWarnings("unchecked")
    @Transient
    @OneToMany(targetEntity = Group.class, mappedBy = "inheritedGroup")
    @Override
    public Set<IGroupPrincipal> getDerivedGroups() {
        if (derivedGroups == null) {
            synchronized (this) {
                if (derivedGroups == null) {
                    derivedGroups = new HashSet<IGroupPrincipal>();
                }
            }
        }
        return (Set<IGroupPrincipal>) derivedGroups;
    }

    public void setDerivedGroups(Set<? extends IGroupPrincipal> derivedGroups) {
        this.derivedGroups = derivedGroups;
    }

    @ManyToOne(targetEntity = User.class)
    @Override
    public IUserPrincipal getOwner() {
        return owner;
    }

    public void setOwner(IUserPrincipal owner) {
        this.owner = owner;
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
    @ManyToMany(targetEntity = User.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "GroupMember", //
    /*            */joinColumns = @JoinColumn(name = "group"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "member"))
    @Override
    public Set<IUserPrincipal> getMemberUsers() {
        if (memberUsers == null) {
            synchronized (this) {
                if (memberUsers == null) {
                    memberUsers = new HashSet<IUserPrincipal>();
                }
            }
        }
        return (Set<IUserPrincipal>) memberUsers;
    }

    @SuppressWarnings("unchecked")
    public void setMemberUsers(Set<? extends IUserPrincipal> memberUsers) {
        this.memberUsers = (Set<IUserPrincipal>) memberUsers;
    }

    @SuppressWarnings("unchecked")
    @ManyToMany(targetEntity = Role.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "GroupRole", //
    /*            */joinColumns = @JoinColumn(name = "group"), //
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

    @Override
    protected int hashCodeEntity() {
        if (!PrincipalBeanConfig.fullEquality)
            return super.hashCodeEntity();

        final int prime = 31;
        int result = 1;
        result = prime * result + ((inheritedGroup == null) ? 0 : inheritedGroup.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((primaryRole == null) ? 0 : primaryRole.hashCode());
        result = prime * result + ((assignedRoles == null) ? 0 : assignedRoles.hashCode());
        result = prime * result + ((memberUsers == null) ? 0 : memberUsers.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        if (!PrincipalBeanConfig.fullEquality)
            return false;

        Group other = (Group) otherEntity;

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
