package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class AbstractUserDto<U extends User>
        extends AbstractPrincipalDto<U> {

    private static final long serialVersionUID = 1L;

    GroupDto primaryGroup;
    RoleDto primaryRole;

    List<GroupDto> assignedGroups;
    List<RoleDto> assignedRoles;

    public AbstractUserDto(int selection) {
        super(selection);
    }

    public AbstractUserDto(int selection, U source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(U source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT)) {
            primaryGroup = new GroupDto(_selection).marshalRec(source.getPrimaryGroup());
            primaryRole = new RoleDto(_selection).marshalRec(source.getPrimaryRole());
        }

        if (selection.contains(GROUPS))
            assignedGroups = marshalListRec(GroupDto.class, _selection, source.getAssignedGroups());

        if (selection.contains(ROLES))
            assignedRoles = marshalListRec(RoleDto.class, _selection, source.getAssignedRoles());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, U target) {
        super._unmarshalTo(context, target);

        if (depth == 0)
            return;

        WithContext with = with(context, target);

        if (selection.contains(EXT)) {
            with.unmarshal("primaryGroup", primaryGroup);
            with.unmarshal("primaryRole", primaryRole);
        }

        if (selection.contains(GROUPS))
            with.unmarshalSet("assignedGroups", assignedGroups);

        if (selection.contains(ROLES))
            with.unmarshalSet("assignedRoles", assignedRoles);
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

}
