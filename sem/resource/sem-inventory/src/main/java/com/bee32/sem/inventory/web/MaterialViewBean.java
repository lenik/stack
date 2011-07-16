package com.bee32.sem.inventory.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;

@Component
@Scope("view")
public class MaterialViewBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    private boolean searching;
    private String materialNamePattern;
    private LazyDataModel<MaterialDto> materialList;
    private TreeNode root;
    private TreeNode selectedMaterialNode;

    MaterialViewBean() {
        root = new DefaultTreeNode("root", null);
    }

    @PostConstruct
    public void initialize() {
        EntityDataModelOptions<Material, MaterialDto> edmo = new EntityDataModelOptions<Material, MaterialDto>(
                Material.class, MaterialDto.class);
        materialList = UIHelper.buildLazyDataModel(edmo);
        refreshMaterialCount(false);
        buildTree();
    }

    public void buildTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(Restrictions.isNull("parent"));
        List<MaterialCategoryDto> materialCategoryDtoList = DTOs.marshalList(MaterialCategoryDto.class, rootCategories);
        for (MaterialCategoryDto mc : materialCategoryDtoList) {
            treeAssembling(mc, root);
        }
    }

    void treeAssembling(MaterialCategoryDto materialCategory, TreeNode parent) {
        TreeNode subParentNode = new DefaultTreeNode(materialCategory, parent);
        List<MaterialCategoryDto> childrenList = materialCategory.getChildren();
        if (childrenList != null)
            for (MaterialCategoryDto mc : childrenList)
                treeAssembling(mc, subParentNode);
    }

    public void refreshMaterialCount(boolean isSearching) {
        int count = serviceFor(Material.class).count(//
                isSearching ? Restrictions.like("name", "%" + materialNamePattern + "%") : null);
        if (isSearching)
            materialList.setRowCount(count);
    }

    public void onNodeSelect(NodeSelectEvent event) {
        MaterialCategory mc = (MaterialCategory) selectedMaterialNode.getData();
        long id = mc.getId();
        System.out.println("=======================");
        System.out.println(id);
    }

    public LazyDataModel<MaterialDto> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(LazyDataModel<MaterialDto> materialList) {
        this.materialList = materialList;
    }

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public String getMaterialNamePattern() {
        return materialNamePattern;
    }

    public void setMaterialNamePattern(String materialNamePattern) {
        this.materialNamePattern = materialNamePattern;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedMaterialNode() {
        return selectedMaterialNode;
    }

    public void setSelectedMaterialNode(TreeNode selectedMaterialNode) {
        this.selectedMaterialNode = selectedMaterialNode;
    }

}
