package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.PrincipalCheckException;
import com.bee32.icsf.principal.PrincipalDiag;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class RoleAdminBean
        extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private boolean editNewStatus;	//true:新增状态;false:修改状态;

	private TreeNode rootNode;
	private RoleDto role;
	private TreeNode selectedInheritedRoleNode;



	public boolean isEditNewStatus() {
		return editNewStatus;
	}

	public void setEditNewStatus(boolean editNewStatus) {
		this.editNewStatus = editNewStatus;
	}

	public TreeNode getRoot() {
        return rootNode;
    }

    public RoleDto getRole() {
        if (role == null) {
            _newRole();
        }
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = reload(role);
    }

    public TreeNode getSelectedInheritedRoleNode() {
		return selectedInheritedRoleNode;
	}

	public void setSelectedInheritedRoleNode(TreeNode selectedInheritedRoleNode) {
		this.selectedInheritedRoleNode = selectedInheritedRoleNode;
	}

	private void loadRoleTree() {
        rootNode = new DefaultTreeNode("root", null);

        List<Role> rootRoles = serviceFor(Role.class).list(Restrictions.isNull("parent"));
        List<RoleDto> rootRoleDtos = DTOs.marshalList(RoleDto.class, -1, rootRoles, true);

        for (RoleDto roleDto : rootRoleDtos) {
            loadRoleRecursive(roleDto, rootNode);
        }
    }

    private void loadRoleRecursive(RoleDto roleDto, TreeNode parentTreeNode) {
        TreeNode roleNode = new DefaultTreeNode(roleDto, parentTreeNode);

        List<RoleDto> subRoles = roleDto.getDerivedRoles();
        for (RoleDto subRole : subRoles) {
            loadRoleRecursive(subRole, roleNode);
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
		editNewStatus = true;
		_newRole();
	}

	public void doModifyRole() {
		editNewStatus = false;
	}


    public void doSave() {
	if(editNewStatus) {
		//新增
	        Role existing = serviceFor(Role.class).get(role.getId());
	        if (existing != null) {
	            uiLogger.error("保存失败:角色已存在。");
	            return;
	        }
	}

        Role r = role.unmarshal(this);

        try {
            PrincipalDiag.checkDeadLoop(r);
        } catch (PrincipalCheckException e) {
            uiLogger.error("角色结构非法:角色存在循环引用", e);
            return;
        }

        try {
            serviceFor(Role.class).saveOrUpdate(r);
            loadRoleTree();
            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.错误消息:" + e.getMessage());
        }
    }

    public void doDelete() {
		try {
			serviceFor(Role.class).delete(role.unmarshal());
			loadRoleTree();
			uiLogger.info("删除成功!");
		} catch (DataIntegrityViolationException e) {
			uiLogger.error("删除失败,违反约束归则,可能你需要删除的角色在其它地方被使用到!");
		}
    }

    public void doSelectInheritedRole() {
	role.setInheritedRole((RoleDto) selectedInheritedRoleNode.getData());
    }
}
