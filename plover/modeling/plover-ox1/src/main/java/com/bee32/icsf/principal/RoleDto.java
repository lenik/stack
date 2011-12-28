package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;

public class RoleDto
        extends AbstractPrincipalDto<Role> {

    private static final long serialVersionUID = 1L;

    RoleDto inheritedRole;

    List<RoleDto> derivedRoles;
    List<UserDto> responsibleUsers;
    List<GroupDto> responsibleGroups;

    public RoleDto() {
        super();
    }

    public RoleDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Role source) {
        super._marshal(source);

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(EXT))
            inheritedRole = mref(RoleDto.class, _selection, source.getInheritedRole());
        else
            inheritedRole = new RoleDto(); // XXX?

        if (selection.contains(ROLES))
            derivedRoles = marshalList(RoleDto.class, _selection, source.getDerivedRoles());
        else
            derivedRoles = new ArrayList<RoleDto>();

        if (selection.contains(USERS))
            responsibleUsers = marshalList(UserDto.class, _selection, source.getResponsibleUsers());
        else
            responsibleUsers = new ArrayList<UserDto>();

        if (selection.contains(GROUPS))
            responsibleGroups = marshalList(GroupDto.class, _selection, source.getResponsibleGroups());
        else
            responsibleGroups = new ArrayList<GroupDto>();
    }

    @Override
    protected void _unmarshalTo(Role target) {
        super._unmarshalTo(target);

        if (selection.contains(EXT))
            merge(target, "inheritedRole", inheritedRole);

        if (selection.contains(ROLES))
            mergeList(target, "derivedRoles", derivedRoles);

        if (selection.contains(USERS))
            mergeList(target, "responsibleUsers", responsibleUsers);

        if (selection.contains(GROUPS))
            mergeList(target, "responsibleGroups", responsibleGroups);
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

    public boolean addDerivedRole(RoleDto derivedRole) {
        if (derivedRole == null)
            throw new NullPointerException("derivedRole");

        if (derivedRoles.contains(derivedRole))
            return false;

        derivedRoles.add(derivedRole);
        return true;
    }

    public boolean removeDerivedRole(RoleDto derivedRole) {
        return derivedRoles.remove(derivedRole);
    }

    public List<UserDto> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<UserDto> responsibleUsers) {
        if (responsibleUsers == null)
            throw new NullPointerException("responsibleUsers");
        this.responsibleUsers = responsibleUsers;
    }

    public boolean addResponsibleUser(UserDto responsibleUser) {
        if (responsibleUser == null)
            throw new NullPointerException("responsibleUser");

        if (responsibleUsers.contains(responsibleUser))
            return false;

        responsibleUsers.add(responsibleUser);
        return true;
    }

    public boolean removeResponsibleUser(UserDto responsibleUser) {
        return responsibleUsers.remove(responsibleUser);
    }

    public List<GroupDto> getResponsibleGroups() {
        return responsibleGroups;
    }

    public void setResponsibleGroups(List<GroupDto> responsibleGroups) {
        if (responsibleGroups == null)
            throw new NullPointerException("responsibleGroups");
        this.responsibleGroups = responsibleGroups;
    }

    public boolean addResponsibleGroup(GroupDto responsibleGroup) {
        if (responsibleGroup == null)
            throw new NullPointerException("responsibleGroup");

        if (responsibleGroups.contains(responsibleGroup))
            return false;

        responsibleGroups.add(responsibleGroup);
        return true;
    }

    public boolean removeResponsibleGroup(GroupDto responsibleGroup) {
        return responsibleGroups.remove(responsibleGroup);
    }

}
