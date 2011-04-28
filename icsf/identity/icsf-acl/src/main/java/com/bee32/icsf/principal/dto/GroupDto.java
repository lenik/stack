package com.bee32.icsf.principal.dto;

import java.util.List;
import java.util.Set;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class GroupDto
        extends AbstractPrincipalDto<Group> {

    private static final long serialVersionUID = 1L;

    GroupDto inheritedGroup;
    UserDto owner;
    RoleDto primaryRole;

    List<GroupDto> derivedGroups;
    List<RoleDto> assignedRoles;
    List<UserDto> memberUsers;

    public GroupDto(int selection) {
        super(selection);
    }

    public GroupDto(int selection, Group source) {
        super(selection, source);
    }

    public GroupDto getInheritedGroup() {
        return inheritedGroup;
    }

    public void setInheritedGroup(GroupDto inheritedGroup) {
        this.inheritedGroup = inheritedGroup;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public RoleDto getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(RoleDto primaryRole) {
        this.primaryRole = primaryRole;
    }

    public List<GroupDto> getDerivedGroups() {
        return derivedGroups;
    }

    public void setDerivedGroups(List<GroupDto> derivedGroups) {
        this.derivedGroups = derivedGroups;
    }

    public List<RoleDto> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<RoleDto> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public List<UserDto> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(List<UserDto> memberUsers) {
        this.memberUsers = memberUsers;
    }

    @Override
    protected void _marshal(Group source) {
        super._marshal(source);

        inheritedGroup = new GroupDto(selection.bits).marshalRec(source.getInheritedGroup());
        owner = new UserDto(selection.bits).marshalRec(source.getOwner());
        primaryRole = new RoleDto(selection.bits).marshalRec(source.getPrimaryRole());

        if (selection.contains(GROUPS))
            derivedGroups = marshalListRec(GroupDto.class, selection.bits, source.getDerivedGroups());

        if (selection.contains(ROLES))
            assignedRoles = marshalListRec(RoleDto.class, selection.bits, source.getAssignedRoles());

        if (selection.contains(USERS))
            memberUsers = marshalListRec(UserDto.class, selection.bits, source.getMemberUsers());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Group target) {
        super._unmarshalTo(context, target);

        target.setInheritedGroup(GroupDto.unmarshal(inheritedGroup));
        target.setOwner(UserDto.unmarshal(owner));
        target.setPrimaryRole(RoleDto.unmarshal(primaryRole));

        WithContext<Group> with = with(context, target);

        if (selection.contains(GROUPS))
            with.unmarshalSet(derivedGroupsProperty, derivedGroups);

        if (selection.contains(ROLES))
            with.unmarshalSet(assignedRolesProperty, assignedRoles);

        if (selection.contains(USERS))
            with.unmarshalSet(memberUsersProperty, memberUsers);
    }

    static final PropertyAccessor<Group, Set<Group>> derivedGroupsProperty = new PropertyAccessor<Group, Set<Group>>(
            Set.class) {

        @Override
        public Set<Group> get(Group entity) {
            return entity.getDerivedGroups();
        }

        @Override
        public void set(Group entity, Set<Group> derivedGroups) {
            entity.setDerivedGroups(derivedGroups);
        }

    };

    static final PropertyAccessor<Group, Set<Role>> assignedRolesProperty = new PropertyAccessor<Group, Set<Role>>(
            Set.class) {

        @Override
        public Set<Role> get(Group entity) {
            return entity.getAssignedRoles();
        }

        @Override
        public void set(Group entity, Set<Role> assignedRoles) {
            entity.setAssignedRoles(assignedRoles);
        }

    };

    static final PropertyAccessor<Group, Set<User>> memberUsersProperty = new PropertyAccessor<Group, Set<User>>(
            Set.class) {

        @Override
        public Set<User> get(Group entity) {
            return entity.getMemberUsers();
        }

        @Override
        public void set(Group entity, Set<User> memberUsers) {
            entity.setMemberUsers(memberUsers);
        }

    };

}
