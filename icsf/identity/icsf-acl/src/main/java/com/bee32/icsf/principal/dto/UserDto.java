package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class UserDto
        extends AbstractPrincipalDto<User> {

    private static final long serialVersionUID = 1L;

    public static final int GROUPS = 1;
    public static final int ROLES = 2;

    GroupDto primaryGroup;
    RoleDto primaryRole;

    List<GroupDto> assignedGroups;
    List<RoleDto> assignedRoles;

    public UserDto() {
        super();
    }

    public UserDto(User source) {
        super(source);
    }

    public UserDto(int selection) {
        super(selection);
    }

    public UserDto(User source, int selection) {
        super(source, selection);
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

    public List<RoleDto> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<RoleDto> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    @Override
    protected void _marshal(User source) {
        super._marshal(source);

        primaryGroup = new GroupDto(selection.bits).marshalRec(source.getPrimaryGroup());
        primaryRole = new RoleDto(selection.bits).marshalRec(source.getPrimaryRole());

        if (selection.contains(GROUPS))
            assignedGroups = marshalListRec(GroupDto.class, selection.bits, source.getAssignedGroups());

        if (selection.contains(ROLES))
            assignedRoles = marshalListRec(RoleDto.class, selection.bits, source.getAssignedRoles());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, User target) {
        super._unmarshalTo(context, target);

        target.setPrimaryGroup(GroupDto.unmarshal(primaryGroup));
        target.setPrimaryRole(RoleDto.unmarshal(primaryRole));

        if (selection.contains(GROUPS))
            target.setAssignedGroups(GroupDto.unmarshalSet(assignedGroups));

        if (selection.contains(ROLES))
            target.setAssignedRoles(RoleDto.unmarshalSet(assignedRoles));
    }

}
