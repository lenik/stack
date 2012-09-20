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

/**
 * 用户组
 *
 * 可被用户共享访问资源的用户组。
 */
@Entity
@DiscriminatorValue("G")
public class Group
        extends Principal
        implements IGroupPrincipal {

    private static final long serialVersionUID = 1L;

    Role primaryRole;
    List<Role> assignedRoles = new ArrayList<Role>();
    List<User> controlUsers = new ArrayList<User>();
    List<User> memberUsers = new ArrayList<User>();

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

    @Override
    public void populate(Object source) {
        if (source instanceof Group)
            _populate((Group) source);
        else
            super.populate(source);
    }

    protected void _populate(Group o) {
        super._populate(o);
        primaryRole = o.primaryRole;
        assignedRoles = new ArrayList<Role>(o.assignedRoles);
        controlUsers = new ArrayList<User>(o.controlUsers);
        memberUsers = new ArrayList<User>(o.memberUsers);
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((Group) o);
    }

    private void _retarget(Group o) {
        // primaryRole = _retarget(primaryRole, o.primaryRole);
        // _retargetMerge(assignedRoles, o.assignedRoles);
        // _retargetMerge(controlUsers, o.controlUsers);
        // _retargetMerge(memberUsers, o.memberUsers);
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

    /**
     * 主角色
     *
     *
     * 组所属的主角色。 当以组创建内容时，内容的属角色将被设置为该组的主角色。
     */
    @ManyToOne
    @JoinColumn(name = "role1")
    @Override
    public Role getPrimaryRole() {
        return primaryRole;
    }

    @Override
    public void setPrimaryRole(Role newPrimaryRole) {
        if (OptimConfig.setControlSide) {
            if (this.primaryRole == newPrimaryRole)
                return;

            if (this.primaryRole != null)
                this.primaryRole.removeControlGroup(this);

            if (newPrimaryRole != null)
                newPrimaryRole.addControlGroup(this);
        }
        this.primaryRole = newPrimaryRole;
    }

    /**
     * 主控用户
     *
     * 主组为本组的用户集合。
     */
    @OneToMany(mappedBy = "primaryGroup")
    @Override
    public List<User> getControlUsers() {
        return controlUsers;
    }

    public void setControlUsers(List<User> controlUsers) {
        if (controlUsers == null)
            throw new NullPointerException("controlUsers");
        this.controlUsers = controlUsers;
    }

    @Override
    public boolean addControlUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> controlUsers = getControlUsers();
        if (controlUsers.contains(user))
            return false;

        controlUsers.add(user);
        invalidateRelations();
        return true;
    }

    @Override
    public boolean removeControlUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> controlUsers = getControlUsers();
        if (!controlUsers.remove(user))
            return false;

        invalidateRelations();
        return true;
    }

    /**
     * 成员用户
     *
     * 被赋予本组的用户集合。
     */
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
        if (this.memberUsers != memberUsers) {
            this.memberUsers = memberUsers;
            invalidateRelations();
        }
    }

    @Override
    public boolean addMemberUser(User user) {
        return addMemberUser(user, OptimConfig.setPeerSide);
    }

    public boolean addMemberUser(User user, boolean peer) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> memberUsers = getMemberUsers();
        if (memberUsers.contains(user))
            return false;
        else
            memberUsers.add(user);

        if (peer)
            user.addAssignedGroup(this);

        invalidateRelations();
        return true;
    }

    public boolean removeMemberUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        List<User> memberUsers = getMemberUsers();
        if (!memberUsers.remove(user))
            return false;

        user.removeAssignedGroup(this);

        invalidateRelations();
        return true;
    }

    /**
     * 赋予角色
     *
     * 组被赋予的角色集合。
     */
    @ManyToMany(/* mappedBy = "responsibleGroups" */)
    @JoinTable(name = "RoleGroup", //
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
        return addAssignedRole(role, OptimConfig.setPeerSide);
    }

    public boolean addAssignedRole(Role role, boolean peer) {
        if (role == null)
            throw new NullPointerException("role");

        List<Role> assignedRoles = getAssignedRoles();
        if (assignedRoles.contains(role))
            return false;
        else
            assignedRoles.add(role);

        if (peer)
            role.addResponsibleGroup(this);
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

        Role primaryRole = getPrimaryRole();
        if (primaryRole != null)
            if (primaryRole.implies(principal))
                return true;

        Group base = getInheritedGroup();
        if (base != null)
            if (base.implies(principal))
                return true;

        for (Role role : getAssignedRoles())
            if (role.implies(principal))
                return true;

        return false;
    }

    @Override
    protected void populateImSet(Set<Principal> imSet) {
        super.populateImSet(imSet);

        Role primaryRole = getPrimaryRole();
        if (primaryRole != null)
            primaryRole.populateImSet(imSet);

        Group base = getInheritedGroup();
        if (base != null)
            base.populateImSet(imSet);

        for (Role role : getAssignedRoles())
            role.populateImSet(imSet);
    }

    @Override
    protected void populateInvSet(Set<Principal> invSet) {
        super.populateInvSet(invSet);

        for (User user : getControlUsers())
            invSet.add(user);

        for (User user : getMemberUsers())
            invSet.add(user);
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

        setPrimaryRole(null);

        for (Role role : flyOver(getAssignedRoles()))
            role.removeResponsibleGroup(this);

        for (User user : flyOver(getMemberUsers()))
            user.removeAssignedGroup(this);

        return this;
    }

}
