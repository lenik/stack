package com.bee32.plover.ox1.principal;

import java.util.List;


public class UserDto
        extends AbstractPrincipalDto<User> {

    private static final long serialVersionUID = 1L;

    GroupDto primaryGroup;
    RoleDto primaryRole;

    List<GroupDto> assignedGroups;
    List<RoleDto> assignedRoles;

    public UserDto() {
        super();
    }

    public UserDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(User source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT)) {
            primaryGroup = mref(GroupDto.class, _selection, source.getPrimaryGroup());
            primaryRole = mref(RoleDto.class, _selection, source.getPrimaryRole());
        }

        if (selection.contains(GROUPS))
            assignedGroups = marshalList(GroupDto.class, _selection, source.getAssignedGroups());

        if (selection.contains(ROLES))
            assignedRoles = marshalList(RoleDto.class, _selection, source.getAssignedRoles());
    }

    @Override
    protected void _unmarshalTo(User target) {
        super._unmarshalTo(target);

        if (depth == 0)
            return;

        if (selection.contains(EXT)) {
            merge(target, "primaryGroup", primaryGroup);
            merge(target, "primaryRole", primaryRole);
        }

        if (selection.contains(GROUPS))
            mergeList(target, "assignedGroups", assignedGroups);

        if (selection.contains(ROLES))
            mergeList(target, "assignedRoles", assignedRoles);
    }

    public GroupDto getPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(GroupDto primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    public RoleDto getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(RoleDto primaryRole) {
        this.primaryRole = primaryRole;
    }

    public List<GroupDto> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<GroupDto> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }

    public boolean addAssignedGroup(GroupDto assignedGroup) {
        if (assignedGroup == null)
            throw new NullPointerException("assignedGroup");

        if (assignedGroups.contains(assignedGroup))
            return false;

        assignedGroups.add(assignedGroup);
        return true;
    }

    public boolean removeAssignedGroup(GroupDto assignedGroup) {
        return assignedGroups.remove(assignedGroup);
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

}
