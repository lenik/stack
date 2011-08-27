package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@DiscriminatorValue("U")
public class User
        extends Principal
        implements IUserPrincipal {

    private static final long serialVersionUID = 1L;

    Group primaryGroup;
    Role primaryRole;

    List<Group> assignedGroups = new ArrayList<Group>();
    List<Role> assignedRoles = new ArrayList<Role>();

    List<UserEmail> emails = new ArrayList<UserEmail>();

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
    public List<Group> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<Group> assignedGroups) {
        if (assignedGroups == null)
            throw new NullPointerException("assignedGroups");
        this.assignedGroups = assignedGroups;
    }

    @Override
    public boolean addAssignedGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");

        List<Group> assignedGroups = getAssignedGroups();
        if (assignedGroups.contains(group))
            return false;

        assignedGroups.add(group);
        group.addMemberUser(this);
        return true;
    }

    @Override
    public boolean removeAssignedGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");

        if (!group.removeMemberUser(this))
            return false;

        if (getAssignedGroups().remove(group))
            group.removeMemberUser(this);

        return true;
    }

    @ManyToMany(targetEntity = Role.class)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "UserRole", //
    /*            */joinColumns = @JoinColumn(name = "user"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "role"))
    @Override
    public List<Role> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<Role> assignedRoles) {
        if (assignedRoles == null)
            throw new NullPointerException("assignedRoles");
        this.assignedRoles = assignedRoles;
    }

    @Override
    public boolean addAssignedRole(Role role) {
        if (role == null)
            throw new NullPointerException("role");

        List<Role> assignedRoles = getAssignedRoles();
        if (assignedRoles.contains(role))
            return false;

        assignedRoles.add(role);
        role.removeResponsibleUser(this);
        return true;
    }

    @Override
    public boolean removeAssignedRole(Role role) {
        if (role == null)
            throw new NullPointerException("role");

        if (!getAssignedRoles().remove(role))
            return false;

        role.removeResponsibleUser(this);
        return true;
    }

    @OneToMany(mappedBy = "user")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<UserEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<UserEmail> emails) {
        if (emails == null)
            throw new NullPointerException("emails");
        this.emails = emails;
    }

    @Transient
    public String getPreferredEmail() {
        if (emails.isEmpty())
            return null;
        return emails.get(0).getAddress();
    }

    public void setPreferredEmail(String emailAddress) {
        if (emailAddress == null)
            throw new NullPointerException("emailAddress");

        UserEmail preferredEmail = new UserEmail(this, emailAddress);

        if (emails.contains(preferredEmail))
            emails.remove(preferredEmail);

        // Re-order.
        for (UserEmail prev : emails)
            if (prev.getRank() <= 0)
                prev.setRank(1);
        preferredEmail.setRank(0);

        emails.add(preferredEmail);
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
            return true;

        for (Group group : getAssignedGroups())
            if (group.implies(principal))
                return true;

        for (Role role : getAssignedRoles())
            if (role.implies(principal))
                return true;

        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            for (Group group : getAssignedGroups())
                visitor.visitImplication(group);
            for (Role role : getAssignedRoles())
                visitor.visitImplication(role);
            visitor.endPrincipal(this);
        }
    }

    @Override
    public User detach() {
        super.detach();

        for (Group group : flyOver(getAssignedGroups()))
            group.removeMemberUser(this);

        for (Role role : flyOver(getAssignedRoles()))
            role.removeResponsibleUser(this);

        return this;
    }

    public static User admin = new User("admin", null, Role.adminRole);
    public static User guest = new User("guest", null, Role.guestRole);

}
