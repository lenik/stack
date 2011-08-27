package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("G")
public class Group
        extends Principal
        implements IGroupPrincipal {

    private static final long serialVersionUID = 1L;

    Role primaryRole;

    List<Role> assignedRoles = new ArrayList<Role>();;
    List<User> memberUsers = new ArrayList<User>();;

    public Group() {
    }

    public Group(String name) {
        super(name);
    }

    public Group(String name, String fullName) {
        super(name, fullName);
    }

    public Group(String name, String fullName, User owner, User... memberUsers) {
        super(name, fullName);

        addMemberUser(owner);

        for (User user : memberUsers)
            addMemberUser(user);
    }

    @Transient
    @Override
    public Group getInheritedGroup() {
        return (Group) getParent();
    }

    public void setInheritedGroup(Group inheritedGroup) {
        setParent(inheritedGroup);
    }

    @Transient
    @Override
    public List<Group> getDerivedGroups() {
        return cast(getChildren());
    }

    public void setDerivedGroups(List<Group> derivedGroups) {
        List<Principal> children = cast(derivedGroups);
        setChildren(children);
    }

    @ManyToOne
    @JoinColumn(name = "role1")
    @Override
    public Role getPrimaryRole() {
        return primaryRole;
    }

    @Override
    public void setPrimaryRole(Role primaryRole) {
        this.primaryRole = (Role) primaryRole;
    }

    @ManyToMany
    // @Cascade(CascadeType.SAVE_UPDATE)
    // @Cascade(CascadeType.ALL)
    @JoinTable(name = "GroupMember", //
    /*            */joinColumns = @JoinColumn(name = "group"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "member"))
    @Override
    public List<User> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(List<User> memberUsers) {
        if (memberUsers == null)
            throw new NullPointerException("memberUsers");
        this.memberUsers = memberUsers;
    }

    @Override
    public boolean addMemberUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> memberUsers = getMemberUsers();
        if (memberUsers.contains(user))
            return false;

        memberUsers.add(user);
        user.addAssignedGroup(this);
        return true;
    }

    @Override
    public boolean removeMemberUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        if (!getMemberUsers().remove(user))
            return false;

        user.removeAssignedGroup(this);
        return true;
    }

    @ManyToMany
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "GroupRole", //
    /*            */joinColumns = @JoinColumn(name = "group"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "role"))
    @Override
    public List<Role> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<Role> assignedRoles) {
        this.assignedRoles = (List<Role>) assignedRoles;
    }

    @Override
    public boolean addAssignedRole(Role role) {
        if (role == null)
            throw new NullPointerException("role");

        List<Role> assignedRoles = getAssignedRoles();
        if (assignedRoles.contains(role))
            return false;

        assignedRoles.add(role);
        role.removeResponsibleGroup(this);
        return true;
    }

    @Override
    public boolean removeAssignedRole(Role role) {
        if (role == null)
            throw new NullPointerException("role");

        if (!getAssignedRoles().remove(role))
            return false;

        role.removeResponsibleGroup(this);
        return true;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
            return true;

        IGroupPrincipal base = getInheritedGroup();
        if (base != null)
            if (base.implies(principal))
                return true;

        for (Role role : getAssignedRoles())
            if (role.implies(principal))
                return true;

        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            IGroupPrincipal base = getInheritedGroup();
            if (base != null)
                visitor.visitImplication(base);
            for (Role role : getAssignedRoles())
                visitor.visitImplication(role);
            visitor.endPrincipal(this);
        }
    }

    @Override
    public Group detach() {
        super.detach();

        for (Role role : flyOver(getAssignedRoles()))
            role.removeResponsibleGroup(this);

        for (User user : flyOver(getMemberUsers()))
            user.removeAssignedGroup(this);

        return this;
    }

}
