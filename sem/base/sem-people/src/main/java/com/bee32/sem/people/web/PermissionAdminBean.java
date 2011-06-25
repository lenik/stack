package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.ResourcePermission;
import com.bee32.icsf.access.alt.R_ACLService;
import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.GroupDto;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class PermissionAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private List<RPEntry> rpEntries;
    private RPEntry selectedRpEntry;



    @PostConstruct
    public void init() {

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

	public List<RPEntry> getRpEntries() {
		return rpEntries;
	}

	public void setRpEntries(List<RPEntry> rpEntries) {
		this.rpEntries = rpEntries;
	}

	public RPEntry getSelectedRpEntry() {
		return selectedRpEntry;
	}

	public void setSelectedRpEntry(RPEntry selectedRpEntry) {
		this.selectedRpEntry = selectedRpEntry;
	}




	public void onRowSelectUser(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		UserDto selectedUser = (UserDto) event.getObject();

		if(selectedUser == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要设置权限的用户!"));
            return;
		}

        ScannedResourceRegistry srr = getBean(ScannedResourceRegistry.class);
        R_ACLService aclService = getBean(R_ACLService.class);


        Map<String, ResourcePermission> havePermissions = new HashMap<String, ResourcePermission>();
        List<ResourcePermission> haveResourcePermissions = aclService.getResourcePermissions(selectedUser.unmarshal());
        for(ResourcePermission rp : haveResourcePermissions) {
		String permissionQulifier = srr.qualify(rp.getResource());
		havePermissions.put(permissionQulifier, rp);
        }

	rpEntries = new ArrayList<RPEntry>();
	    for(IResourceNamespace rn: srr.getNamespaces()) {
		for(Resource res: rn.getResources()) {
			String resourceType = ClassUtil.getDisplayName(res.getClass());
			String permissionQulifier = srr.qualify(res);
			RPEntry rpEntry = new RPEntry();
			rpEntry.setResourceType(resourceType);
			rpEntry.setDisplayName(res.getAppearance().getDisplayName());
			rpEntry.setQualifiedName(permissionQulifier);

			if(havePermissions.containsKey(permissionQulifier)) {
				rpEntry.setPermission(havePermissions.get(permissionQulifier).getPermission());
			} else {
				rpEntry.setPermission(new Permission(0));
			}
		}
	    }

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
