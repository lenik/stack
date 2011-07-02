package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("R")
public class Role
        extends Principal<Role>
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

    @Transient
    @Override
    public Role getInheritedRole() {
        return getParent();
    }

    public void setInheritedRole(Role inheritedRole) {
        setParent(inheritedRole);
    }

    @Transient
    @OneToMany(targetEntity = Role.class, mappedBy = "inheritedRole")
    @Override
    public List<Role> getDerivedRoles() {
        return Collections.unmodifiableList(getChildren());
    }

    public void setDerivedRoles(List<Role> derivedRoles) {
        setChildren(derivedRoles);
    }

    @ManyToMany(mappedBy = "assignedRoles")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public List<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<User> responsibleUsers) {
        if (responsibleUsers == null)
            throw new NullPointerException("responsibleUsers");
        this.responsibleUsers = responsibleUsers;
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
        return true;
    }

    @Override
    public boolean removeResponsibleUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        if (!getResponsibleUsers().remove(user))
            return false;

        user.removeAssignedRole(this);
        return true;
    }

    @ManyToMany(mappedBy = "assignedRoles")
    // @Cascade(CascadeType.SAVE_UPDATE)
    @Override
    public List<Group> getResponsibleGroups() {
        return responsibleGroups;
    }

    public void setResponsibleGroups(List<Group> responsibleGroups) {
        if (responsibleGroups == null)
            throw new NullPointerException("responsibleGroups");
        this.responsibleGroups = responsibleGroups;
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
        return true;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
            return true;

        IRolePrincipal base = getInheritedRole();
        if (base != null)
            if (base.implies(principal))
                return true;

        return false;
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

    public static Role adminRole = new Role("adminRole");
    public static Role guestRole = new Role("guestRole");

}
