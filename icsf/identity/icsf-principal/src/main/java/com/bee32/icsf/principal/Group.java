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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@Table(name = "Group")
@DiscriminatorValue("group")
public class Group
        extends AbstractGroup
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected IGroupPrincipal inheritedGroup;
    protected IUserPrincipal owner;
    protected IRolePrincipal primaryRole;

    protected Set<IRolePrincipal> assignedRoles;
    protected Set<IUserPrincipal> memberUsers;

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
        this.inheritedGroup = inheritedGroup;
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

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = User.class)
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
        return memberUsers;
    }

    public void setMemberUsers(Collection<? extends IUserPrincipal> memberUsers) {
        this.memberUsers = new HashSet<IUserPrincipal>(memberUsers);
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    /*            */targetEntity = Role.class)
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
        return assignedRoles;
    }

    public void setAssignedRoles(Collection<? extends IRolePrincipal> assignedRoles) {
        this.assignedRoles = new HashSet<IRolePrincipal>(assignedRoles);
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
