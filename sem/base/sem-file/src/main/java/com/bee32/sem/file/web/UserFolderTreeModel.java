package com.bee32.sem.file.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.ox1.tree.TreeEntityUtils;
import com.bee32.sem.file.dto.UserFolderDto;
import com.bee32.sem.file.entity.UserFolder;
import com.bee32.sem.sandbox.ITreeNodeDecorator;
import com.bee32.sem.sandbox.UIHelper;

@NotAComponent
public class UserFolderTreeModel
        extends DataViewBean
        implements ITreeNodeDecorator, Serializable {

    private static final long serialVersionUID = 1L;

    final ICriteriaElement[] criteriaElements;

    TreeNode rootNode;
    Map<Integer, UserFolderDto> index;
    TreeNode selectedNode;

    public UserFolderTreeModel(ICriteriaElement... criteriaElements) {
        if (criteriaElements == null)
            throw new NullPointerException("criteriaElements");
        this.criteriaElements = criteriaElements;
    }

    public TreeNode getRootNode() {
        loadTree();
        return rootNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode node) {
        this.selectedNode = node;
    }

    public Integer getSelectedId() {
        if (selectedNode == null)
            return null;
        UserFolderDto selectedFolder = (UserFolderDto) selectedNode.getData();
        if (selectedFolder == null)
            return null;
        return selectedFolder.getId();
    }

    synchronized void loadTree() {
        if (rootNode == null) {
            List<UserFolder> _categories = DATA(UserFolder.class).list(criteriaElements);
            List<UserFolderDto> categories = DTOs.mrefList(UserFolderDto.class, //
                    TreeEntityDto.PARENT , //
                    _categories);
            index = DTOs.index(categories);
            Set<UserFolderDto> roots = TreeEntityUtils.rebuildTree(index);

            rootNode = new DefaultTreeNode("folderRoot", null);
            UIHelper.buildTree(this, roots, rootNode);
        }
    }

    @Override
    public void decorate(TreeNode node) {
        UserFolderDto folder = (UserFolderDto) node.getData();
        if (folder != null) {
            Integer folderId = folder.getId();
            if (folderId.equals(getSelectedId()))
                node.setSelected(true);
        }
    }

    public Map<Integer, UserFolderDto> getIndex() {
        loadTree();
        return index;
    }

}
