package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class RoleAdminBean
        extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private RoleDto role;
	private RoleDto selectedRole;
	private RoleDto selectedParentRole;


	public TreeNode getRoot() {
		if(root == null) {
			loadRoleTree();
		}
		return root;
	}

	public RoleDto getRole() {
		if(role == null) {
			_newRole();
		}
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	public RoleDto getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(RoleDto selectedRole) {
		this.selectedRole = selectedRole;
	}

	public RoleDto getSelectedParentRole() {
		return selectedParentRole;
	}

	public void setSelectedParentRole(RoleDto selectedParentRole) {
		this.selectedParentRole = selectedParentRole;
	}



	private void loadRoleTree() {
		root = new DefaultTreeNode("根", null);

		List<Role> roles = serviceFor(Role.class).list(Restrictions.isNull("inheritedRole"));	//get top roles
        List<RoleDto> roleDtos = DTOs.marshalList(RoleDto.class, roles);

        for(RoleDto roleDto : roleDtos) {
		loadRoleRecursive(roleDto, root);
        }
	}

	private void loadRoleRecursive(RoleDto roleDto, TreeNode parentTreeNode) {
		TreeNode roleNode = new DefaultTreeNode(roleDto, parentTreeNode);

	List<RoleDto> subRoles = roleDto.getDerivedRoles();
	if(subRoles.size() > 0) {
		for(RoleDto subRole : subRoles) {
			loadRoleRecursive(subRole, roleNode);
		}
	}
	}

	@PostConstruct
	public void init() {
		loadRoleTree();
	}

	private void _newRole() {
		role = new RoleDto();
	}

	public void doNewRole() {
		_newRole();
	}


}
