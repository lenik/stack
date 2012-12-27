package com.bee32.sem.material.web;

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
import com.bee32.sem.material.dto.MaterialCategoryDto;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.sandbox.ITreeNodeDecorator;
import com.bee32.sem.sandbox.UIHelper;

@NotAComponent
public class MaterialCategoryTreeModel
        extends DataViewBean
        implements ITreeNodeDecorator, Serializable {

    private static final long serialVersionUID = 1L;

    final ICriteriaElement[] criteriaElements;

    TreeNode rootNode;
    Map<Integer, MaterialCategoryDto> index;
    TreeNode selectedNode;

    public MaterialCategoryTreeModel(ICriteriaElement... criteriaElements) {
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
        MaterialCategoryDto selectedCategory = (MaterialCategoryDto) selectedNode.getData();
        if (selectedCategory == null) // may be virtual-node?
            return null;
        return selectedCategory.getId();
    }

    synchronized void loadTree() {
        if (rootNode == null) {
            List<MaterialCategory> _categories = DATA(MaterialCategory.class).list(criteriaElements);
            List<MaterialCategoryDto> categories = DTOs.mrefList(MaterialCategoryDto.class, //
                    TreeEntityDto.PARENT | MaterialCategoryDto.PART_COUNTS, //
                    _categories);
            index = DTOs.index(categories);
            Set<MaterialCategoryDto> roots = TreeEntityUtils.rebuildTree(index);

            rootNode = new DefaultTreeNode("categoryRoot", null);
            UIHelper.buildTree(this, roots, rootNode);
        }
    }

    @Override
    public void decorate(TreeNode node) {
        MaterialCategoryDto category = (MaterialCategoryDto) node.getData();
        if (category != null) {
            Integer categoryId = category.getId();
            if (categoryId.equals(getSelectedId()))
                node.setSelected(true);
        }
    }

    public Map<Integer, MaterialCategoryDto> getIndex() {
        loadTree();
        return index;
    }

}
