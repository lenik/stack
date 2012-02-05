package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;

public class UserDto
        extends PrincipalDto {

    private static final long serialVersionUID = 1L;

    public static final int PRIMARIES = 1;
    public static final int GROUPS = 2;
    public static final int ROLES = 4;

    GroupDto primaryGroup;
    RoleDto primaryRole;

    List<GroupDto> assignedGroups = new ArrayList<GroupDto>();
    List<RoleDto> assignedRoles = new ArrayList<RoleDto>();

    public UserDto() {
        super();
    }

    public UserDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Principal _source) {
        super._marshal(_source);
        User source = (User) _source;

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(PRIMARIES)) {
            primaryGroup = mref(GroupDto.class, _selection, source.getPrimaryGroup());
            primaryRole = mref(RoleDto.class, _selection, source.getPrimaryRole());
        }

        if (selection.contains(GROUPS))
            assignedGroups = mrefList(GroupDto.class, _selection, source.getAssignedGroups());
        else
            assignedGroups = new ArrayList<GroupDto>();

        if (selection.contains(ROLES))
            assignedRoles = mrefList(RoleDto.class, _selection, source.getAssignedRoles());
        else
            assignedRoles = new ArrayList<RoleDto>();
    }

    @Override
    protected void _unmarshalTo(Principal _target) {
        super._unmarshalTo(_target);
        User target = (User) _target;

        if (depth == 0)
            return;

        if (selection.contains(PRIMARIES)) {
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

    public void clearPrimaryGroup() {
        primaryGroup = new GroupDto().ref();
    }

    public RoleDto getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(RoleDto primaryRole) {
        this.primaryRole = primaryRole;
    }

    public void clearPrimaryRole() {
        primaryRole = new RoleDto().ref();
    }

    public List<GroupDto> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<GroupDto> assignedGroups) {
        if (assignedGroups == null)
            throw new NullPointerException("assignedGroups");
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
        if (assignedRoles == null)
            throw new NullPointerException("assignedRoles");
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

    public GroupDto getFirstGroup() {
        if (primaryGroup != null)
            return primaryGroup;
        if (assignedGroups.isEmpty())
            return null;
        GroupDto firstAssignedGroup = assignedGroups.get(0);
        return firstAssignedGroup;
    }

    public RoleDto getFirstRole() {
        if (primaryRole != null)
            return primaryRole;
        if (assignedRoles.isEmpty())
            return null;
        RoleDto firstAssignedRole = assignedRoles.get(0);
        return firstAssignedRole;
    }

}
