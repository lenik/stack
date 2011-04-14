package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.Group;

public class GroupDto
        extends PrincipalDto<Group> {

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

    public GroupDto(Group source, int selection) {
        super(source, selection);
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

        inheritedGroup = new GroupDto(selection.bits).marshal(source.getInheritedGroup());
        owner = new UserDto(selection.bits).marshal(source.getOwner());
        primaryRole = new RoleDto(selection.bits).marshal(source.getPrimaryRole());

        if (selection.contains(GROUPS))
            derivedGroups = GroupDto.marshalList(selection.bits, source.getDerivedGroups());

        if (selection.contains(ROLES))
            assignedRoles = RoleDto.marshalList(selection.bits, source.getAssignedRoles());

        if (selection.contains(USERS))
            memberUsers = UserDto.marshalList(selection.bits, source.getMemberUsers());
    }

    @Override
    protected void _unmarshalTo(Group target) {
        super._unmarshalTo(target);

        target.setInheritedGroup(GroupDto.unmarshal(inheritedGroup));
        target.setOwner(UserDto.unmarshal(owner));
        target.setPrimaryRole(RoleDto.unmarshal(primaryRole));

        if (selection.contains(GROUPS))
            target.setDerivedGroups(GroupDto.unmarshalSet(derivedGroups));

        if (selection.contains(ROLES))
            target.setAssignedRoles(RoleDto.unmarshalSet(assignedRoles));

        if (selection.contains(USERS))
            target.setMemberUsers(UserDto.unmarshalSet(memberUsers));
    }

    public static List<GroupDto> marshalList(int selection, Iterable<? extends Group> entities) {
        return marshalList(GroupDto.class, selection, entities);
    }

}
