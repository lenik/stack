package com.bee32.icsf.principal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.access.alt.R_ACE;

@Entity
@DiscriminatorValue("U")
public class User
        extends AbstractUser {

    private static final long serialVersionUID = 1L;

    Group primaryGroup;
    Role primaryRole;

    Set<Group> assignedGroups;
    Set<Role> assignedRoles;

    List<UserEmail> emails;

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

    @OneToMany
    @Cascade(CascadeType.ALL)
    public List<UserEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<UserEmail> emails) {
        this.emails = emails;
    }

    @Override
    public User detach() {
        super.detach();
        return this;
    }

    public static User admin = new User("admin", null, Role.adminRole);
    public static User guest = new User("guest", null, Role.guestRole);
    public static R_ACE adminApAll = new R_ACE("ap:", admin, "Srwxlcd");
    public static R_ACE adminEntityAll = new R_ACE("entity:", admin, "Srwxlcd");

}
