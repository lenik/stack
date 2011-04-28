package com.bee32.icsf.principal.dto;

import java.util.List;
import java.util.Set;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class RoleDto
        extends AbstractPrincipalDto<Role> {

    private static final long serialVersionUID = 1L;

    RoleDto inheritedRole;

    List<RoleDto> derivedRoles;
    List<UserDto> responsibleUsers;
    List<GroupDto> responsibleGroups;

    public RoleDto(int selection) {
        super(selection);
    }

    public RoleDto(int selection, Role source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(Role source) {
        super._marshal(source);

        inheritedRole = new RoleDto(selection.bits).marshalRec(source.getInheritedRole());

        if (selection.contains(ROLES))
            derivedRoles = marshalListRec(RoleDto.class, selection.bits, source.getDerivedRoles());

        if (selection.contains(USERS))
            responsibleUsers = marshalListRec(UserDto.class, selection.bits, source.getResponsibleUsers());

        if (selection.contains(GROUPS))
            responsibleGroups = marshalListRec(GroupDto.class, selection.bits, source.getResponsibleGroups());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Role target) {
        super._unmarshalTo(context, target);

        target.setInheritedRole(GroupDto.unmarshal(inheritedRole));

        WithContext<Role> with = with(context, target);

        if (selection.contains(ROLES))
            with.unmarshalSet(derivedRolesProperty, derivedRoles);

        if (selection.contains(USERS))
            with.unmarshalSet(responsibleUsersProperty, responsibleUsers);

        if (selection.contains(GROUPS))
            with.unmarshalSet(responsibleGroupsProperty, responsibleGroups);
    }

    static final PropertyAccessor<Role, Set<Role>> derivedRolesProperty = new PropertyAccessor<Role, Set<Role>>(
            Set.class) {

        @Override
        public Set<Role> get(Role entity) {
            return entity.getDerivedRoles();
        }

        @Override
        public void set(Role entity, Set<Role> derivedRoles) {
            entity.setDerivedRoles(derivedRoles);
        }

    };
    static final PropertyAccessor<Role, Set<User>> responsibleUsersProperty = new PropertyAccessor<Role, Set<User>>(
            Set.class) {

        @Override
        public Set<User> get(Role entity) {
            return entity.getResponsibleUsers();
        }

        @Override
        public void set(Role entity, Set<User> responsibleUsers) {
            entity.setResponsibleUsers(responsibleUsers);
        }

    };

    static final PropertyAccessor<Role, Set<Group>> responsibleGroupsProperty = new PropertyAccessor<Role, Set<Group>>(
            Set.class) {

        @Override
        public Set<Group> get(Role entity) {
            return entity.getResponsibleGroups();
        }

        @Override
        public void set(Role entity, Set<Group> responsibleGroups) {
            entity.setResponsibleGroups(responsibleGroups);
        }

    };

}
