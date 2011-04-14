package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.Role;

public class RoleDto
        extends PrincipalDto<Role> {

    private static final long serialVersionUID = 1L;

    RoleDto inheritedRole;

    List<RoleDto> derivedRoles;
    List<UserDto> responsibleUsers;
    List<GroupDto> responsibleGroups;

    public RoleDto(int selection) {
        super(selection);
    }

    public RoleDto(Role source, int selection) {
        super(source, selection);
    }

    @Override
    protected void _marshal(Role source) {
        super._marshal(source);

        inheritedRole = new RoleDto(selection.bits).marshal(source.getInheritedRole());

        if (selection.contains(ROLES))
            derivedRoles = RoleDto.marshalList(selection.bits, source.getDerivedRoles());

        if (selection.contains(USERS))
            responsibleUsers = UserDto.marshalList(selection.bits, source.getResponsibleUsers());

        if (selection.contains(GROUPS))
            responsibleGroups = GroupDto.marshalList(selection.bits, source.getResponsibleGroups());
    }

    @Override
    protected void _unmarshalTo(Role target) {
        super._unmarshalTo(target);

        target.setInheritedRole(GroupDto.unmarshal(inheritedRole));

        if (selection.contains(ROLES))
            target.setDerivedRoles(RoleDto.unmarshalSet(derivedRoles));

        if (selection.contains(USERS))
            target.setResponsibleUsers(UserDto.unmarshalSet(responsibleUsers));

        if (selection.contains(GROUPS))
            target.setResponsibleGroups(GroupDto.unmarshalSet(responsibleGroups));
    }

    public static List<RoleDto> marshalList(int selection, Iterable<? extends Role> entities) {
        return marshalList(RoleDto.class, selection, entities);
    }

}
