package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("R")
public class Role
        extends Principal
        implements IRolePrincipal {

    private static final long serialVersionUID = 1L;

    List<User> responsibleUsers = new ArrayList<User>();
    List<Group> responsibleGroups = new ArrayList<Group>();

    public Role() {
        super();
    }

    public Role(String name) {
        super(name);
    }

    public Role(String name, String fullName) {
        super(name, fullName);
    }

    @Transient
    @Override
    public Role getInheritedRole() {
        return (Role) getParent();
    }

    public void setInheritedRole(Role inheritedRole) {
        setParent(inheritedRole);
    }

    @Transient
    @Override
    public List<Role> getDerivedRoles() {
        return cast(getChildren());
    }

    public void setDerivedRoles(List<Role> derivedRoles) {
        List<Principal> children = cast(derivedRoles);
        setChildren(children);
    }

    @ManyToMany
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "RoleUser", //
    /*            */joinColumns = @JoinColumn(name = "role"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "user"))
    @Override
    public List<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<User> responsibleUsers) {
        if (responsibleUsers == null)
            throw new NullPointerException("responsibleUsers");
        if (this.responsibleUsers != responsibleUsers) {
            this.responsibleUsers = responsibleUsers;
            invalidateRelations();
        }
    }

    @Override
    public boolean addResponsibleUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> responsibleUsers = (List<User>) getResponsibleUsers();
        if (responsibleUsers.contains(user))
            return false;

        responsibleUsers.add(user);
        user.addAssignedRole(this);
        invalidateRelations();
        return true;
    }

    @Override
    public boolean removeResponsibleUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        if (!getResponsibleUsers().remove(user))
            return false;

        user.removeAssignedRole(this);
        invalidateRelations();
        return true;
    }

    @ManyToMany
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "RoleGroup", //
    /*            */joinColumns = @JoinColumn(name = "role"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "group"))
    @Override
    public List<Group> getResponsibleGroups() {
        return responsibleGroups;
    }

    public void setResponsibleGroups(List<Group> responsibleGroups) {
        if (responsibleGroups == null)
            throw new NullPointerException("responsibleGroups");
        if (this.responsibleGroups != responsibleGroups) {
            this.responsibleGroups = responsibleGroups;
            invalidateRelations();
        }
    }

    @Override
    public boolean addResponsibleGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");

        List<Group> responsibleGroups = getResponsibleGroups();
        if (responsibleGroups.contains(group))
            return false;

        responsibleGroups.add(group);
        group.addAssignedRole(this);
        invalidateRelations();
        return true;
    }

    @Override
    public boolean removeResponsibleGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");

        List<Group> responsibleGroups = getResponsibleGroups();
        if (!responsibleGroups.remove(group))
            return false;

        group.removeAssignedRole(this);
        invalidateRelations();
        return true;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
            return true;

        Role base = getInheritedRole();
        if (base != null)
            if (base.implies(principal))
                return true;

        return false;
    }

    @Override
    protected void populateImSet(Set<Principal> imSet) {
        super.populateImSet(imSet);

        Role base = getInheritedRole();
        if (base != null)
            base.populateImSet(imSet);
    }

    @Override
    protected void populateInvSet(Set<Principal> invSet) {
        super.populateInvSet(invSet);

        for (User user : responsibleUsers)
            user.populateInvSet(invSet);

        for (Group group : responsibleGroups)
            group.populateInvSet(invSet);
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            IRolePrincipal base = getInheritedRole();
            if (base != null)
                visitor.visitImplication(base);
            visitor.endPrincipal(this);
        }
    }

    @Override
    public Role detach() {
        super.detach();

        for (Group group : flyOver(getResponsibleGroups()))
            group.removeAssignedRole(this);

        for (User user : flyOver(getResponsibleUsers()))
            user.removeAssignedRole(this);

        return this;
    }

    public static Role adminRole = new Role("adminRole", "Administrator Users");
    public static Role powerUserRole = new Role("powerUserRole", "Powerful Users");
    public static Role userRole = new Role("userRole", "Registered Users");
    public static Role guestRole = new Role("guestRole", "Guest Users");

    static {
        powerUserRole.setInheritedRole(userRole);
    }

}
