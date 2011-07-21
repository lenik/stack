package com.bee32.sem.inventory.web.dialogs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.sem.frame.ui.ISelectionListener;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;

public class StockLocationTreeDialogModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    TreeNode rootNode;
    TreeNode selectedNode;

    List<ISelectionListener> selectListeners = new ArrayList<ISelectionListener>();

    public StockLocationTreeDialogModel(List<StockLocationDto> rootLocations) {
        Map<StockWarehouseDto, TreeNode> _warehouseNodes = new HashMap<StockWarehouseDto, TreeNode>();

        rootNode = new DefaultTreeNode("root", null);

        for (StockLocationDto location : rootLocations) {
            TreeNode parentNode = getWarehouseNode(location, _warehouseNodes);
            DefaultTreeNode node = new DefaultTreeNode(location, parentNode);

            for (StockLocationDto child : location.getChildren())
                buildTree(child, node);
        }

    }

    void buildTree(StockLocationDto location, TreeNode parentNode) {
        DefaultTreeNode node = new DefaultTreeNode(location, parentNode);

        for (StockLocationDto child : location.getChildren())
            buildTree(child, node);
    }

    TreeNode getWarehouseNode(StockLocationDto locationDto, Map<StockWarehouseDto, TreeNode> warehouseNodes) {
        StockWarehouseDto warehouse = locationDto.getWarehouse();

        TreeNode node = warehouseNodes.get(warehouse);
        if (node == null) {
            node = new DefaultTreeNode(warehouse, rootNode);
            warehouseNodes.put(warehouse, node);
        }

        return node;
    }

    public void select() {
        Object data = null;
        if (selectedNode != null)
            data = selectedNode.getData();

        SelectionEvent selectEvent = new SelectionEvent(this, data);

        for (ISelectionListener listener : selectListeners)
            listener.itemSelected(selectEvent);
    }

    public void addSelectListener(ISelectionListener listener) {
        if (listener == null)
            throw new NullPointerException("listener");
        selectListeners.add(listener);
    }

    public void removeSelectListener(ISelectionListener listener) {
        if (listener == null)
            throw new NullPointerException("listener");
        selectListeners.remove(listener);
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

}
