package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.bee32.plover.orm.entity.CopyUtils;

/**
 * 用户
 *
 * 可登录到系统的用户帐户。
 */
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

    public User(String name, String fullName) {
        super(name);
        setFullName(fullName);
    }

    public User(String name, Group primaryGroup, Role primaryRole) {
        super(name);
        this.primaryGroup = primaryGroup;
        this.primaryRole = primaryRole;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof User)
            _populate((User) source);
        else
            super.populate(source);
    }

    protected void _populate(User o) {
        super._populate(o);
        primaryGroup = o.primaryGroup;
        primaryRole = o.primaryRole;
        assignedGroups = new ArrayList<Group>(o.assignedGroups);
        assignedRoles = new ArrayList<Role>(o.assignedRoles);
        emails = CopyUtils.copyList(o.emails);
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((User) o);
    }

    private void _retarget(User o) {
        // primaryGroup = _retarget(primaryGroup, o.primaryGroup);
        // primaryRole = _retarget(primaryRole, o.primaryRole);
        // _retargetMerge(assignedGroups, o.assignedGroups);
        // _retargetMerge(assignedRoles, o.assignedRoles);
        _retargetMerge(emails, o.emails);
    }

    /**
     * 主组
     *
     * 用户所属的主组。 当用户创建内容时，内容的属组将被设置为登录用户的主组。
     */
    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "group1")
    @Override
    public Group getPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(Group newPrimaryGroup) {
        if (OptimConfig.setControlSide) {
            if (this.primaryGroup == newPrimaryGroup)
                return;

            if (this.primaryGroup != null)
                this.primaryGroup.removeControlUser(this);

            if (newPrimaryGroup != null)
                newPrimaryGroup.addControlUser(this);
        }
        this.primaryGroup = newPrimaryGroup;
    }

    /**
     * 主角色
     *
     * 用户所属的主角色。 当用户创建内容时，内容的属角色将被设置为登录用户的主角色。
     */
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role1")
    @Override
    public Role getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(Role newPrimaryRole) {
        if (OptimConfig.setControlSide) {
            if (this.primaryRole == newPrimaryRole)
                return;

            if (this.primaryRole != null)
                this.primaryRole.removeControlUser(this);

            if (newPrimaryRole != null)
                newPrimaryRole.addControlUser(this);
        }
        this.primaryRole = newPrimaryRole;
    }

    /**
     * 赋予组
     *
     * 用户被赋予的组集合。
     */
    @ManyToMany(/* mappedBy = "memberUsers" */)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "GroupMember", //
    /*            */joinColumns = @JoinColumn(name = "member"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "group"))
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
        else
            assignedGroups.add(group);

        if (OptimConfig.setPeerSide)
            group.addMemberUser(this);
        return true;
    }

    @Override
    public boolean removeAssignedGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");

        List<Group> assignedGroups = getAssignedGroups();
        if (!assignedGroups.remove(group))
            return false;

        if (OptimConfig.setPeerSide)
            group.removeMemberUser(this);

        return true;
    }

    /**
     * 赋予角色
     *
     * 用户被赋予的角色集合。
     */
    @ManyToMany(/* mappedBy = "responsibleUsers" */)
    // @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "RoleUser", //
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

        List<Role> assignedRoles = getAssignedRoles();
        if (!assignedRoles.remove(role))
            return false;

        if (OptimConfig.setPeerSide)
            role.removeResponsibleUser(this);

        return true;
    }

    @Transient
    public Group getFirstGroup() {
        if (primaryGroup != null)
            return primaryGroup;
        if (assignedGroups.isEmpty())
            return null;
        Group firstAssignedGroup = assignedGroups.get(0);
        return firstAssignedGroup;
    }

    @Transient
    public Role getFirstRole() {
        if (primaryRole != null)
            return primaryRole;
        if (assignedRoles.isEmpty())
            return null;
        Role firstAssignedRole = assignedRoles.get(0);
        return firstAssignedRole;
    }

    /**
     * 邮箱地址
     *
     * 用户对应的邮箱地址列表。
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
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

        Group primaryGroup = getPrimaryGroup();
        if (primaryGroup != null)
            if (primaryGroup.implies(principal))
                return true;

        Role primaryRole = getPrimaryRole();
        if (primaryRole != null)
            if (primaryRole.implies(principal))
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
    protected void populateImSet(Set<Principal> imSet) {
        super.populateImSet(imSet);

        Group primaryGroup = getPrimaryGroup();
        if (primaryGroup != null)
            primaryGroup.populateImSet(imSet);

        Role primaryRole = getPrimaryRole();
        if (primaryRole != null)
            primaryRole.populateImSet(imSet);

        for (Group group : getAssignedGroups())
            group.populateImSet(imSet);

        for (Role role : getAssignedRoles())
            role.populateImSet(imSet);
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

        setPrimaryGroup(null);
        setPrimaryRole(null);

        for (Group group : flyOver(getAssignedGroups()))
            group.removeMemberUser(this);

        for (Role role : flyOver(getAssignedRoles()))
            role.removeResponsibleUser(this);

        return this;
    }

}
