package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 角色
 *
 * 共用角色的用户对资源具有相同的访问权限。
 */
@Entity
@DiscriminatorValue("R")
public class Role
        extends Principal
        implements IRolePrincipal {

    private static final long serialVersionUID = 1L;

    List<User> controlUsers = new ArrayList<User>();
    List<Group> controlGroups = new ArrayList<Group>();

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

    @Override
    public void populate(Object source) {
        if (source instanceof Role)
            _populate((Role) source);
        else
            super.populate(source);
    }

    protected void _populate(Role o) {
        super._populate(o);
        controlUsers = new ArrayList<User>(o.controlUsers);
        controlGroups = new ArrayList<Group>(o.controlGroups);
        responsibleUsers = new ArrayList<User>(o.responsibleUsers);
        responsibleGroups = new ArrayList<Group>(o.responsibleGroups);
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((Role) o);
    }

    private void _retarget(Role o) {
        // _retargetMerge(controlUsers, o.controlUsers);
        // _retargetMerge(controlGroups, o.controlGroups);
        // _retargetMerge(responsibleUsers, o.responsibleUsers);
        // _retargetMerge(responsibleGroups, o.responsibleGroups);
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

    /**
     * 主控用户
     *
     * 以本角色为主角色的用户集合。
     */
    @OneToMany(mappedBy = "primaryRole")
    @Override
    public List<User> getControlUsers() {
        return controlUsers;
    }

    public void setControlUsers(List<User> controlUsers) {
        if (controlUsers == null)
            throw new NullPointerException("controlUsers");
        if (this.controlUsers != controlUsers) {
            this.controlUsers = controlUsers;
            invalidateRelations();
        }
    }

    @Override
    public boolean addControlUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> controlUsers = (List<User>) getControlUsers();
        if (controlUsers.contains(user))
            return false;
        else
            controlUsers.add(user);
        invalidateRelations();
        return true;
    }

    @Override
    public boolean removeControlUser(User user) {
        List<User> controlUsers = (List<User>) getControlUsers();
        if (!controlUsers.remove(user))
            return false;
        invalidateRelations();
        return true;
    }

    /**
     * 主控组
     *
     * 以本角色为主角色的组集合。
     */
    @OneToMany(mappedBy = "primaryRole")
    @Override
    public List<Group> getControlGroups() {
        return controlGroups;
    }

    public void setControlGroups(List<Group> controlGroups) {
        if (controlGroups == null)
            throw new NullPointerException("controlGroups");
        if (this.controlGroups != controlGroups) {
            this.controlGroups = controlGroups;
            invalidateRelations();
        }
    }

    @Override
    public boolean addControlGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");

        List<Group> controlGroups = (List<Group>) getControlGroups();
        if (controlGroups.contains(group))
            return false;
        else
            controlGroups.add(group);
        invalidateRelations();
        return true;
    }

    @Override
    public boolean removeControlGroup(Group group) {
        List<Group> controlGroups = (List<Group>) getControlGroups();
        if (!controlGroups.remove(group))
            return false;
        invalidateRelations();
        return true;
    }

    /**
     * 责任用户
     *
     * 被赋予本角色的用户集。
     */
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
        else
            responsibleUsers.add(user);

        if (OptimConfig.setPeerSide)
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

        if (OptimConfig.setPeerSide)
            user.removeAssignedRole(this);

        invalidateRelations();
        return true;
    }

    /**
     * 责任组
     *
     * 被赋予本角色的组集。
     */
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
        else
            responsibleGroups.add(group);

        if (OptimConfig.setPeerSide)
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

        if (OptimConfig.setPeerSide)
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

        for (User user : controlUsers)
            user.populateInvSet(invSet);

        for (Group group : controlGroups)
            group.populateInvSet(invSet);

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

}
