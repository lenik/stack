package com.bee32.sem.inventory.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.ox1.tree.TreeEntityUtils;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.sandbox.ITreeNodeDecorator;
import com.bee32.sem.sandbox.UIHelper;

@NotAComponent
public class MaterialCategoryTreeModel
        extends DataViewBean
        implements ITreeNodeDecorator, Serializable {

    private static final long serialVersionUID = 1L;

    Set<MaterialType> materialTypes = new HashSet<MaterialType>();
    TreeNode rootNode;
    Map<Integer, MaterialCategoryDto> index;
    TreeNode selectedNode;

    public Set<MaterialType> getMaterialTypes() {
        return materialTypes;
    }

    public void addMaterialType(MaterialType materialType) {
        if (materialType == null)
            throw new NullPointerException("materialType");
        materialTypes.add(materialType);
    }

    public void removeMaterialType(MaterialType materialType) {
        materialTypes.remove(materialType);
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
            List<MaterialCategory> _categories = ctx.data.access(MaterialCategory.class).list(
                    MaterialCriteria.categoryType(materialTypes));
            List<MaterialCategoryDto> categories = DTOs.mrefList(MaterialCategoryDto.class, TreeEntityDto.PARENT,
                    _categories);
            index = DTOs.index(categories);
            Set<MaterialCategoryDto> roots = TreeEntityUtils.rebuildTree(categories, index);

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
