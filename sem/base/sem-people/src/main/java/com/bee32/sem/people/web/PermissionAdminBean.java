package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.ResourcePermission;
import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACLService;
import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.AbstractPrincipalDto;
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


    private UserDto selectedUser;
    private GroupDto selectedGroup;
    private RoleDto selectedRole;


    private List<RPEntry> rpEntries;
    private RPEntry selectedRpEntry;

    private int activeTab;


    public UserDto getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDto selectedUser) {
		this.selectedUser = selectedUser;
	}

	public GroupDto getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(GroupDto selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public RoleDto getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(RoleDto selectedRole) {
		this.selectedRole = selectedRole;
	}

	public List<UserDto> getUsers() {
        List<User> users = serviceFor(User.class).list();
        List<UserDto> userDtos = DTOs.marshalList(UserDto.class, users);
        return userDtos;
    }

    public List<RoleDto> getRoles() {
        List<Role> roles = serviceFor(Role.class).list();
        List<RoleDto> roleDtos = DTOs.marshalList(RoleDto.class, roles);
        return roleDtos;
    }

    public List<GroupDto> getGroups() {
        List<Group> groups = serviceFor(Group.class).list();
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

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}









	private void loadEntries(AbstractPrincipalDto principalDto) {
		ScannedResourceRegistry srr = getBean(ScannedResourceRegistry.class);
		R_ACLService aclService = getBean(R_ACLService.class);


		Map<String, ResourcePermission> havePermissions = new HashMap<String, ResourcePermission>();
		Principal principal = (Principal) principalDto.unmarshal();
		List<ResourcePermission> haveResourcePermissions = aclService.getResourcePermissions(principal);
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

				rpEntries.add(rpEntry);
			}
		}
	}

	public void onRowSelectUser(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		UserDto clickOn = (UserDto) event.getObject();

		if(clickOn == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要设置权限的用户!"));
            return;
		}

		loadEntries(clickOn);
    }


    public void onRowUnselectUser(UnselectEvent event) {
	rpEntries = new ArrayList<RPEntry>();
    }

    public void onRowSelectRole(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		RoleDto clickOn = (RoleDto) event.getObject();

		if(clickOn == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要设置权限的角色!"));
            return;
		}

		loadEntries(clickOn);
    }

    public void onRowUnselectRole(UnselectEvent event) {
	rpEntries = new ArrayList<RPEntry>();
    }

    public void onRowSelectGroup(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		GroupDto clickOn = (GroupDto) event.getObject();

		if(clickOn == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要设置权限的组!"));
            return;
		}

		loadEntries(clickOn);
    }

    public void onRowUnselectGroup(UnselectEvent event) {
	rpEntries = new ArrayList<RPEntry>();
    }

    /**
     *
     *  save:
     *  	1, delete from R_ACE where principal = currentUser
     *
     *		2, for entry: RPEntry[]
     *			ace = new R_ACE(entry.getResource(), currentUser, entry.permission)
     *    		dataManager.save(ace)
     */
	public void doSave() {
		FacesContext context = FacesContext.getCurrentInstance();

		AbstractPrincipalDto principalDto = null;

		switch (activeTab) {
		case 0:
			principalDto = selectedGroup;
			break;
		case 1:
			principalDto = selectedRole;
			break;
		case 2:
			principalDto = selectedUser;
			break;
		}


		if (principalDto == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要设置权限的主体!"));
			return;
		}

		Principal principal = (Principal) principalDto.unmarshal();

		serviceFor(R_ACE.class).deleteAll(Restrictions.eq("principal", principal));

		ScannedResourceRegistry srr = getBean(ScannedResourceRegistry.class);

		Iterator<RPEntry> iterator = rpEntries.iterator();
		while (iterator.hasNext()) {
			RPEntry rpEntry = (RPEntry) iterator.next();

			Resource resource = srr.query(rpEntry.getQualifiedName());
			R_ACE ace = new  R_ACE(resource, principal, rpEntry.permission);

			serviceFor(R_ACE.class).save(ace);
		}

		context.addMessage(null, new FacesMessage("提示", "权限保存成功!"));

	}


	public void onSelectAllAdmin() {
		for(RPEntry e : rpEntries) {
			e.permission.setAdmin(true);
		}
	}

	public void onSelectInvertAdmin() {
		for(RPEntry e : rpEntries) {
			e.permission.setAdmin(!e.permission.isAdmin());
		}
	}

	public void onSelectNoneAdmin() {
		for(RPEntry e : rpEntries) {
			e.permission.setAdmin(false);
		}
	}

	public void onSelectAllReadable() {
		for(RPEntry e : rpEntries) {
			e.permission.setReadable(true);
		}
	}

	public void onSelectInvertReadable() {
		for(RPEntry e : rpEntries) {
			e.permission.setReadable(!e.permission.isReadable());
		}
	}

	public void onSelectNoneReadable() {
		for(RPEntry e : rpEntries) {
			e.permission.setReadable(false);
		}
	}

}
