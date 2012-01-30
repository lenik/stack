package com.bee32.sem.sandbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.primefaces.model.TreeNode;

public class TreeNodeSelectionHolder
        extends SelectionHolder {

    private static final long serialVersionUID = 1L;

    TreeNode selectedNode;

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    @Override
    public List<?> getSelection() {
        if (selectedNode == null)
            return Collections.emptyList();
        Object data = selectedNode.getData();
        ArrayList<Object> list = new ArrayList<Object>(1);
        if (data != null)
            list.add(data);
        return list;
    }

    @Override
    public void setSelection(List<?> selection) {
        throw new UnsupportedOperationException();
    }

}
