package com.bee32.sem.inventory.web.dialogs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.sem.frame.ui.ISelectionListener;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;

public class MaterialCategoryTreeModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    TreeNode rootNode;
    TreeNode selectedNode;
    TreeNode selectedCategory;

    List<ISelectionListener> selectionListeners = new ArrayList<ISelectionListener>();

    public MaterialCategoryTreeModel(List<MaterialCategoryDto> rootCategoryDtos) {
        rootNode = new DefaultTreeNode("root", null);
        for (MaterialCategoryDto materialCategoryDto : rootCategoryDtos)
            buildCategoryTree(materialCategoryDto, rootNode);
    }

    void buildCategoryTree(MaterialCategoryDto materialCategory, TreeNode parent) {
        TreeNode node = new DefaultTreeNode(materialCategory, parent);
        for (MaterialCategoryDto children : materialCategory.getChildren())
            buildCategoryTree(children, node);
    }

    public void onNodeSelect() {
        Object data = null;
        if (selectedNode != null)
            data = selectedNode.getData();
        SelectionEvent selectEvent = new SelectionEvent(this, data);
        for (ISelectionListener listener : selectionListeners) {
            listener.itemSelected(selectEvent);
        }
    }

    public void onCategorySelect() {
        Object data = null;
        if (selectedCategory != null)
            data = selectedCategory.getData();
        SelectionEvent selectEvent = new SelectionEvent(this, data);
        for (ISelectionListener listener : selectionListeners) {
            listener.itemSelected(selectEvent);
        }
    }

    public void addListner(ISelectionListener selectionListener) {
        if (selectionListener == null)
            throw new NullPointerException("selectionListener");
        selectionListeners.add(selectionListener);
    }

    public void removeListener(ISelectionListener selectionListener) {
        if (selectionListener == null)
            throw new NullPointerException("selectionListener");
        selectionListeners.remove(selectionListener);
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(TreeNode selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

}
