package com.bee32.icsf.principal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("G")
public class Group
        extends AbstractGroup {

    private static final long serialVersionUID = 1L;

    Group inheritedGroup;
    User owner;
    Role primaryRole;

    Set<Group> derivedGroups;

    Set<Role> assignedRoles;
    Set<User> memberUsers;

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

    public void setInheritedGroup(IGroupPrincipal inheritedGroup) {
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

    @ManyToOne
    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public void setOwner(IUserPrincipal owner) {
        this.owner = (User) owner;
    }

    @ManyToOne
    @JoinColumn(name = "role1")
    @Override
    public Role getPrimaryRole() {
        return primaryRole;
    }

    @Override
    public void setPrimaryRole(IRolePrincipal primaryRole) {
        this.primaryRole = (Role) primaryRole;
    }

    @ManyToMany
    // @Cascade(CascadeType.SAVE_UPDATE)
    // @Cascade(CascadeType.ALL)
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

    @ManyToMany
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

}
