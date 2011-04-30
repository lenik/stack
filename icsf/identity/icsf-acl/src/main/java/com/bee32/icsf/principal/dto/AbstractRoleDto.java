package com.bee32.icsf.principal.dto;

import java.util.List;

import com.bee32.icsf.principal.Role;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class AbstractRoleDto<R extends Role>
        extends AbstractPrincipalDto<R> {

    private static final long serialVersionUID = 1L;

    RoleDto inheritedRole;

    List<RoleDto> derivedRoles;
    List<UserDto> responsibleUsers;
    List<GroupDto> responsibleGroups;

    public AbstractRoleDto(int selection) {
        super(selection);
    }

    public AbstractRoleDto(int selection, R source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(R source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT))
            inheritedRole = new RoleDto(_selection).marshalRec(source.getInheritedRole());

        if (selection.contains(ROLES))
            derivedRoles = marshalListRec(RoleDto.class, _selection, source.getDerivedRoles());

        if (selection.contains(USERS))
            responsibleUsers = marshalListRec(UserDto.class, _selection, source.getResponsibleUsers());

        if (selection.contains(GROUPS))
            responsibleGroups = marshalListRec(GroupDto.class, _selection, source.getResponsibleGroups());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, R target) {
        super._unmarshalTo(context, target);

        WithContext with = with(context, target);

        if (selection.contains(EXT))
            with.unmarshal("inheritedRole", inheritedRole);

        if (selection.contains(ROLES))
            with.unmarshalSet("derivedRoles", derivedRoles);

        if (selection.contains(USERS))
            with.unmarshalSet("responsibleUsers", responsibleUsers);

        if (selection.contains(GROUPS))
            with.unmarshalSet("responsibleGroups", responsibleGroups);
    }

}
