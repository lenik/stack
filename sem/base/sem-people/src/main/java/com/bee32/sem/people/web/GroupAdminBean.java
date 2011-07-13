package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.dto.GroupDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class GroupAdminBean
        extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private boolean editNewStatus;	//true:新增状态;false:修改状态;

	private TreeNode rootNode;
	private GroupDto group;
	private TreeNode selectedInheritedGroupNode;


	public boolean isEditNewStatus() {
		return editNewStatus;
	}

	public void setEditNewStatus(boolean editNewStatus) {
		this.editNewStatus = editNewStatus;
	}

	public TreeNode getRoot() {
        return rootNode;
    }

    public GroupDto getGroup() {
        if (group == null) {
            _newGroup();
        }
        return group;
    }

    public void setGroup(GroupDto group) {
        this.group = group;
    }

    public TreeNode getSelectedInheritedGroupNode() {
		return selectedInheritedGroupNode;
	}

	public void setSelectedInheritedGroupNode(TreeNode selectedInheritedGroupNode) {
		this.selectedInheritedGroupNode = selectedInheritedGroupNode;
	}

	private void loadGroupTree() {
        rootNode = new DefaultTreeNode("root", null);

        List<Group> rootGroups = serviceFor(Group.class).list(Restrictions.isNull("parent"));
        List<GroupDto> rootGroupDtos = DTOs.marshalList(GroupDto.class, -1, rootGroups);

        for (GroupDto groupDto : rootGroupDtos) {
            loadGroupRecursive(groupDto, rootNode);
        }
    }

    private void loadGroupRecursive(GroupDto groupDto, TreeNode parentTreeNode) {
        TreeNode groupNode = new DefaultTreeNode(groupDto, parentTreeNode);

        List<GroupDto> subGroups = groupDto.getDerivedGroups();
        for (GroupDto subGroup : subGroups) {
            loadGroupRecursive(subGroup, groupNode);
        }
    }

    @PostConstruct
    public void init() {
	loadGroupTree();
	}

	private void _newGroup() {
		group = new GroupDto();
	}

	public void doNewGroup() {
		editNewStatus = true;
		_newGroup();
	}

	public void doModifyGroup() {
		editNewStatus = false;
	}


    public void doSave() {
	if(editNewStatus) {
		//新增
		Group existing = serviceFor(Group.class).get(group.getId());
	        if (existing != null) {
	            uiLogger.error("保存失败:组已存在。");
	            return;
	        }
	}

	Group g = group.unmarshal(this);

		try {
			serviceFor(Group.class).saveOrUpdate(g);

			TreeNode newTreeNode = null;

			if (group.getInheritedGroup() != null) {
				newTreeNode = new DefaultTreeNode(group,
						selectedInheritedGroupNode);
			} else {
				newTreeNode = new DefaultTreeNode(group, rootNode);
			}

			if (!editNewStatus) {
				// 修改
				TreeNode node = findNodeByGroup(rootNode, group);
				TreeNode parentNode = node.getParent();

				for (TreeNode subNode : node.getChildren()) {
					newTreeNode.getChildren().add(subNode);
				}
				parentNode.getChildren().remove(node);
			}

			uiLogger.info("保存成功。");
		} catch (Exception e) {
			uiLogger.error("保存失败.错误消息:" + e.getMessage());
		}
    }

    public void doDelete() {
		try {
			serviceFor(Group.class).delete(group.unmarshal());
			TreeNode node = findNodeByGroup(rootNode, group);
			node.getParent().getChildren().remove(node);
			uiLogger.info("删除成功!");
		} catch (DataIntegrityViolationException e) {
			uiLogger.error("删除失败,违反约束归则,可能你需要删除的组在其它地方被使用到!");
		}
    }

    public void doSelectInheritedGroup() {
	group.setInheritedGroup((GroupDto) selectedInheritedGroupNode.getData());
    }


    private TreeNode findNodeByGroup(TreeNode node, GroupDto groupDto) {
	if (node.getData() instanceof GroupDto) {
		if(((GroupDto)node.getData()).getName().equals(groupDto.getName())) {
			return node;
		}
		}

	TreeNode resultNode = null;

		if(node.getChildCount() > 0) {
			for(TreeNode subNode : node.getChildren()) {
				resultNode = findNodeByGroup(subNode, groupDto);
				if(resultNode != null) break;
			}
		}

	return resultNode;
    }

}
