package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.Group;

public class AbstractGroupDto<G extends Group>
        extends AbstractPrincipalDto<G> {

    private static final long serialVersionUID = 1L;

    GroupDto inheritedGroup;
    UserDto owner;
    RoleDto primaryRole;

    List<GroupDto> derivedGroups;
    List<RoleDto> assignedRoles;
    List<UserDto> memberUsers;

    public AbstractGroupDto(int selection) {
        super(selection);
    }

    public AbstractGroupDto(int selection, G source) {
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
    protected void _marshal(G source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT)) {
            inheritedGroup = new GroupDto(_selection).marshal(source.getInheritedGroup());
            owner = new UserDto(_selection).marshal(source.getOwner());
            primaryRole = new RoleDto(_selection).marshal(source.getPrimaryRole());
        }

        if (selection.contains(GROUPS))
            derivedGroups = marshalList(GroupDto.class, _selection, source.getDerivedGroups());

        if (selection.contains(ROLES))
            assignedRoles = marshalList(RoleDto.class, _selection, source.getAssignedRoles());

        if (selection.contains(USERS))
            memberUsers = marshalList(UserDto.class, _selection, source.getMemberUsers());
    }

    @Override
    protected void _unmarshalTo(G target) {
        super._unmarshalTo(target);

        if (selection.contains(EXT)) {
            merge(target, "inheritedGroup", inheritedGroup);
            merge(target, "owner", owner);
            merge(target, "primaryRole", primaryRole);
        }

        if (selection.contains(GROUPS))
            mergeSet(target, "derivedGroups", derivedGroups);

        if (selection.contains(ROLES))
            mergeSet(target, "assignedRoles", assignedRoles);

        if (selection.contains(USERS))
            mergeSet(target, "memberUsers", memberUsers);
    }

}
