package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.GroupDto;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class PermissionAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private UserDto selectedUser;
    private RoleDto selectedRole;
    private GroupDto selectedGroup;

    private Resource selectedPermission;


    @PostConstruct
    public void init() {

    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<UserDto> getUsers() {
        List<User> users = getDataManager().loadAll(User.class);
        List<UserDto> userDtos = DTOs.marshalList(UserDto.class, users);

        return userDtos;
    }

    public List<RoleDto> getRoles() {
        List<Role> roles = getDataManager().loadAll(Role.class);
        List<RoleDto> roleDtos = DTOs.marshalList(RoleDto.class, roles);

        return roleDtos;
    }

    public List<GroupDto> getGroups() {
        List<Group> groups = getDataManager().loadAll(Group.class);
        List<GroupDto> groupDtos = DTOs.marshalList(GroupDto.class, groups);

        return groupDtos;
    }

    public List<Resource> getPermissions() {
        ScannedResourceRegistry srr = getBean(ScannedResourceRegistry.class);

	List<Resource> resources = new ArrayList<Resource>();

	    for(IResourceNamespace rn: srr.getNamespaces()) {
		for(Resource res: rn.getResources()) {
			resources.add(res);
		}
	    }

	    return resources;
    }

    public Resource getSelectedPermission() {
		return selectedPermission;
	}

	public void setSelectedPermission(Resource selectedPermission) {
		this.selectedPermission = selectedPermission;
	}







	public void onRowSelectUser(SelectEvent event) {
    }

    public void onRowUnselectUser(UnselectEvent event) {
    }

    public void onRowSelectRole(SelectEvent event) {
    }

    public void onRowUnselectRole(UnselectEvent event) {
    }

    public void onRowSelectGroup(SelectEvent event) {
    }

    public void onRowUnselectGroup(UnselectEvent event) {
    }

}
