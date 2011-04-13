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

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
@DiscriminatorValue("G")
public class Group
        extends AbstractGroup
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Group inheritedGroup;
    protected User owner;
    protected Role primaryRole;

    protected Set<Group> derivedGroups;

    protected Set<Role> assignedRoles;
    protected Set<User> memberUsers;

    public Group() {
    }

    public Group(String name) {
        super(name);
    }

    public Group(String name, User owner, User... memberUsers) {
        super(name);
        this.owner = owner;

        addMemberUser(owner);

        for (IUserPrincipal user : memberUsers)
            addMemberUser(user);
    }

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "parent")
    @Override
    public Group getInheritedGroup() {
        return inheritedGroup;
    }

    public void setInheritedGroup(Group inheritedGroup) {
        this.inheritedGroup = cast(inheritedGroup);
    }

    @OneToMany(targetEntity = Group.class, mappedBy = "inheritedGroup")
    @Override
    public Set<Group> getDerivedGroups() {
        if (derivedGroups == null) {
            synchronized (this) {
                if (derivedGroups == null) {
                    derivedGroups = new HashSet<Group>();
                }
            }
        }
        return derivedGroups;
    }

    @SuppressWarnings("unchecked")
    public void setDerivedGroups(Set<? extends Group> derivedGroups) {
        this.derivedGroups = (Set<Group>) derivedGroups;
    }

    @ManyToOne(targetEntity = User.class)
    @Override
    public IUserPrincipal getOwner() {
        return owner;
    }

    public void setOwner(IUserPrincipal owner) {
        this.owner = (User) owner;
    }

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role1")
    @Override
    public Role getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(IRolePrincipal primaryRole) {
        this.primaryRole = (Role) primaryRole;
    }

    @ManyToMany(targetEntity = User.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "GroupMember", //
    /*            */joinColumns = @JoinColumn(name = "group"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "member"))
    @Override
    public Set<User> getMemberUsers() {
        if (memberUsers == null) {
            synchronized (this) {
                if (memberUsers == null) {
                    memberUsers = new HashSet<User>();
                }
            }
        }
        return memberUsers;
    }

    @SuppressWarnings("unchecked")
    public void setMemberUsers(Set<? extends User> memberUsers) {
        this.memberUsers = (Set<User>) memberUsers;
    }

    @ManyToMany(targetEntity = Role.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "GroupRole", //
    /*            */joinColumns = @JoinColumn(name = "group"), //
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

    @SuppressWarnings("unchecked")
    public void setAssignedRoles(Set<? extends Role> assignedRoles) {
        this.assignedRoles = (Set<Role>) assignedRoles;
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
