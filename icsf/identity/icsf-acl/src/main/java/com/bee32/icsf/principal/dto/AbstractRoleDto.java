package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.Role;

public class AbstractRoleDto<R extends Role>
        extends AbstractPrincipalDto<R> {

    private static final long serialVersionUID = 1L;

    RoleDto inheritedRole;

    List<RoleDto> derivedRoles;
    List<UserDto> responsibleUsers;
    List<GroupDto> responsibleGroups;

    public AbstractRoleDto() {
        super();
    }

    public AbstractRoleDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(R source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT))
            inheritedRole = mref(RoleDto.class, _selection, source.getInheritedRole());

        if (selection.contains(ROLES))
            derivedRoles = marshalList(RoleDto.class, _selection, source.getDerivedRoles());

        if (selection.contains(USERS))
            responsibleUsers = marshalList(UserDto.class, _selection, source.getResponsibleUsers());

        if (selection.contains(GROUPS))
            responsibleGroups = marshalList(GroupDto.class, _selection, source.getResponsibleGroups());
    }

    @Override
    protected void _unmarshalTo(R target) {
        super._unmarshalTo(target);

        if (selection.contains(EXT))
            merge(target, "inheritedRole", inheritedRole);

        if (selection.contains(ROLES))
            mergeSet(target, "derivedRoles", derivedRoles);

        if (selection.contains(USERS))
            mergeSet(target, "responsibleUsers", responsibleUsers);

        if (selection.contains(GROUPS))
            mergeSet(target, "responsibleGroups", responsibleGroups);
    }

    public RoleDto getInheritedRole() {
        return inheritedRole;
    }

    public void setInheritedRole(RoleDto inheritedRole) {
        this.inheritedRole = inheritedRole;
    }

    public List<RoleDto> getDerivedRoles() {
        return derivedRoles;
    }

    public void setDerivedRoles(List<RoleDto> derivedRoles) {
        if (derivedRoles == null)
            throw new NullPointerException("derivedRoles");
        this.derivedRoles = derivedRoles;
    }

    public List<UserDto> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<UserDto> responsibleUsers) {
        if (responsibleUsers == null)
            throw new NullPointerException("responsibleUsers");
        this.responsibleUsers = responsibleUsers;
    }

    public List<GroupDto> getResponsibleGroups() {
        return responsibleGroups;
    }

    public void setResponsibleGroups(List<GroupDto> responsibleGroups) {
        if (responsibleGroups == null)
            throw new NullPointerException("responsibleGroups");
        this.responsibleGroups = responsibleGroups;
    }

}
