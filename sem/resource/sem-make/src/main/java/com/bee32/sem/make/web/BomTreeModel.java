package com.bee32.sem.make.web;

import java.io.Serializable;
import java.math.BigDecimal;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.make.dto.PartItemDto;

@NotAComponent
public class BomTreeModel
        extends DataViewBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    TreeNode rootNode;
    TreeNode selectedNode;

    PartDto topPart;

    public BomTreeModel(PartDto topPart) {
        this.topPart = topPart;
    }


    public TreeNode getRootNode() {
        loadTree();
        return rootNode;
    }


    synchronized void loadTree() {
        if (rootNode == null) {
            rootNode = new DefaultTreeNode("bomRoot", null);

            BomTreeNode bomTreeNode = new BomTreeNode();
            bomTreeNode.setId(topPart.getId());
            bomTreeNode.setMaterial(topPart.getTarget());
            bomTreeNode.setQuantity(new BigDecimal(1));
            bomTreeNode.setMakeStep(true);

            TreeNode topPartNode = new DefaultTreeNode(bomTreeNode.getIdString(), bomTreeNode, rootNode);

            buildTree(topPart, topPartNode);
        }
    }

    private void buildTree(PartDto part, TreeNode parentNode) {
        for (PartItemDto partItem : part.getChildren()) {
            PartDto childPart = partItem.getPart();
            BomTreeNode bomTreeNode = new BomTreeNode();
            bomTreeNode.setQuantity(partItem.getQuantity());
            if(childPart == null || DTOs.isNull(childPart)) {
                //原材料
                bomTreeNode.setId(-partItem.getMaterial().getId().intValue());  //原材料id不使用，所以加上一个负号
                bomTreeNode.setMaterial(partItem.getMaterial());
                bomTreeNode.setPart(null);
                bomTreeNode.setMakeStep(false);
            } else {
                //半成品
                bomTreeNode.setId(childPart.getId());
                bomTreeNode.setMaterial(childPart.getTarget());
                bomTreeNode.setPart(childPart);
                bomTreeNode.setMakeStep(true);

            }

            TreeNode childNode = new DefaultTreeNode(bomTreeNode.getIdString(), bomTreeNode, parentNode);
            if(childPart != null && (!DTOs.isNull(childPart)))
                buildTree(childPart, childNode);
        }
    }



    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode node) {
        this.selectedNode = node;
    }


}
