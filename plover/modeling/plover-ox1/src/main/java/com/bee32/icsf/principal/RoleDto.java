package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;

public class RoleDto
        extends PrincipalDto {

    private static final long serialVersionUID = 1L;

    public static final int USERS = 2;
    public static final int GROUPS = 4;

    List<UserDto> responsibleUsers = new ArrayList<UserDto>();
    List<GroupDto> responsibleGroups = new ArrayList<GroupDto>();

    public RoleDto() {
        super();
    }

    public RoleDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Principal _source) {
        super._marshal(_source);
        Role source = (Role) _source;

        if (depth == 0)
            return;

        int _depth = depth - 1;
        int _selection = DEPTH_MASK.compose(selection.bits, _depth);

        if (selection.contains(USERS))
            responsibleUsers = mrefList(UserDto.class, _selection, source.getResponsibleUsers());
        else
            responsibleUsers = new ArrayList<UserDto>();

        if (selection.contains(GROUPS))
            responsibleGroups = mrefList(GroupDto.class, _selection, source.getResponsibleGroups());
        else
            responsibleGroups = new ArrayList<GroupDto>();
    }

    @Override
    protected void _unmarshalTo(Principal _target) {
        super._unmarshalTo(_target);
        Role target = (Role) _target;

        if (selection.contains(USERS))
            mergeList(target, "responsibleUsers", responsibleUsers);

        if (selection.contains(GROUPS))
            mergeList(target, "responsibleGroups", responsibleGroups);
    }

    public RoleDto getInheritedRole() {
        return (RoleDto) getParent();
    }

    public void setInheritedRole(RoleDto inheritedRole) {
        setParent(inheritedRole);
    }

    public void clearInheritedRole() {
        clearParent();
    }

    public List<? extends RoleDto> getDerivedRoles() {
        return (List<? extends RoleDto>) getChildren();
    }

    public void setDerivedRoles(List<RoleDto> derivedRoles) {
        if (derivedRoles == null)
            throw new NullPointerException("derivedRoles");
        setChildren(derivedRoles);
    }

    public boolean addDerivedRole(RoleDto derivedRole) {
        if (derivedRole == null)
            throw new NullPointerException("derivedRole");
        return addUniqueChild(derivedRole);
    }

    public boolean removeDerivedRole(RoleDto derivedRole) {
        return removeChild(derivedRole);
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
