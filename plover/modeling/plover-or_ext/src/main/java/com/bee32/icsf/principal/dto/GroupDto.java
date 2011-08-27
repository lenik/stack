package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.Group;

public class GroupDto
        extends AbstractPrincipalDto<Group> {

    private static final long serialVersionUID = 1L;

    GroupDto inheritedGroup;
    RoleDto primaryRole;

    List<GroupDto> derivedGroups;
    List<RoleDto> assignedRoles;
    List<UserDto> memberUsers;

    public GroupDto() {
        super();
    }

    public GroupDto(int selection) {
        super(selection);
    }

    public GroupDto getInheritedGroup() {
        return inheritedGroup;
    }

    @Override
    protected void _marshal(Group source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT)) {
            inheritedGroup = mref(GroupDto.class, _selection, source.getInheritedGroup());
            primaryRole = mref(RoleDto.class, _selection, source.getPrimaryRole());
        }

        if (selection.contains(GROUPS))
            derivedGroups = marshalList(GroupDto.class, _selection, source.getDerivedGroups());

        if (selection.contains(ROLES))
            assignedRoles = marshalList(RoleDto.class, _selection, source.getAssignedRoles());

        if (selection.contains(USERS))
            memberUsers = marshalList(UserDto.class, _selection, source.getMemberUsers());
    }

    @Override
    protected void _unmarshalTo(Group target) {
        super._unmarshalTo(target);

        if (selection.contains(EXT)) {
            merge(target, "inheritedGroup", inheritedGroup);
            merge(target, "primaryRole", primaryRole);
        }

        if (selection.contains(GROUPS))
            mergeList(target, "derivedGroups", derivedGroups);

        if (selection.contains(ROLES))
            mergeList(target, "assignedRoles", assignedRoles);

        if (selection.contains(USERS))
            mergeList(target, "memberUsers", memberUsers);
    }

    public void setInheritedGroup(GroupDto inheritedGroup) {
        this.inheritedGroup = inheritedGroup;
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

    public boolean addDerivedGroup(GroupDto derivedGroup) {
        if (derivedGroup == null)
            throw new NullPointerException("derivedGroup");

        if (derivedGroups.contains(derivedGroup))
            return false;

        derivedGroups.add(derivedGroup);
        return true;
    }

    public boolean removeDerivedGroup(GroupDto derivedGroup) {
        return derivedGroups.remove(derivedGroup);
    }

    public List<RoleDto> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<RoleDto> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    public boolean addAssignedRole(RoleDto assignedRole) {
        if (assignedRole == null)
            throw new NullPointerException("assignedRole");

        if (assignedRoles.contains(assignedRole))
            return false;

        assignedRoles.add(assignedRole);
        return true;
    }

    public boolean removeAssignedRole(RoleDto assignedRole) {
        return assignedRoles.remove(assignedRole);
    }

    public List<UserDto> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(List<UserDto> memberUsers) {
        this.memberUsers = memberUsers;
    }

    public boolean addMemberUser(UserDto memberUser) {
        if (memberUser == null)
            throw new NullPointerException("memberUser");

        if (memberUsers.contains(memberUser))
            return false;

        memberUsers.add(memberUser);
        return true;
    }

    public boolean removeMemberUser(UserDto memberUser) {
        return memberUsers.remove(memberUser);
    }

}
