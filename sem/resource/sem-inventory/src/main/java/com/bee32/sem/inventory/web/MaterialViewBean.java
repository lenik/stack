package com.bee32.sem.inventory.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
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

    MaterialViewBean() {
        root = new DefaultTreeNode("root", null);
    }

    @PostConstruct
    public void initialize() {
        EntityDataModelOptions<Material, MaterialDto> edmo = new EntityDataModelOptions<Material, MaterialDto>(
                Material.class, MaterialDto.class);
        materialList = UIHelper.buildLazyDataModel(edmo);
        materialList.setRowCount(1);
        treePolishing();
    }

    public void treePolishing() {
        List<MaterialCategory> allCategory = serviceFor(MaterialCategory.class).list(Restrictions.isNull("parent"));
        List<MaterialCategoryDto> allCategoryDto = DTOs.marshalList(MaterialCategoryDto.class, 0, allCategory);
        for (MaterialCategoryDto mcd : allCategoryDto) {
            TreeNode secondLayer = new DefaultTreeNode(mcd.getCodeGenerator().getName(), root);
        }
    }

    public void refreshMaterialCount(boolean isSearching) {
        int count = serviceFor(Material.class).count(//
                isSearching ? Restrictions.like("name", "%" + materialNamePattern + "%") : null);
        if (isSearching)
            materialList.setRowCount(count);
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

}
