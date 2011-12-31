 package com.bee32.sem.inventory.web;

import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitDto;

public class MaterialExAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    TreeNode root;

    TreeNode selectedMaterialCategoryNode;

    List<MaterialDto> materials;

    MaterialDto material = new MaterialDto().create();

    public MaterialExAdminBean() {
        loadMaterialCategoryTree();
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedMaterialCategoryNode() {
        return selectedMaterialCategoryNode;
    }

    public void setSelectedMaterialCategoryNode(
            TreeNode selectedMaterialCategoryNode) {
        this.selectedMaterialCategoryNode = selectedMaterialCategoryNode;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }


    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    private void loadMaterialCategoryTree() {
        root = new DefaultTreeNode("categoryRoot", null);

        List<MaterialCategory> topCategories = serviceFor(
                MaterialCategory.class).list(//
                TreeCriteria.root());
        List<MaterialCategoryDto> topCategoryDtos = DTOs.mrefList(
                MaterialCategoryDto.class, ~MaterialCategoryDto.MATERIALS,
                topCategories);

        for (MaterialCategoryDto materialCategoryDto : topCategoryDtos) {
            loadMaterialCategoryRecursive(materialCategoryDto, root);
        }
    }

    private void loadMaterialCategoryRecursive(MaterialCategoryDto materialCategoryDto, TreeNode parentTreeNode) {
        TreeNode materialCategoryNode = new DefaultTreeNode(materialCategoryDto, parentTreeNode);

        List<MaterialCategoryDto> subMaterialCategories = materialCategoryDto.getChildren();
        if (subMaterialCategories != null) {
            for (MaterialCategoryDto subMaterialCategory : subMaterialCategories) {
                loadMaterialCategoryRecursive(subMaterialCategory, materialCategoryNode);
            }
        }
    }

    public void onCategorySelect() {
        MaterialCategoryDto category = (MaterialCategoryDto)selectedMaterialCategoryNode.getData();

        category = reload(category, MaterialCategoryDto.MATERIALS);

        materials = category.getMaterials();
    }

    public void newMaterial() {
        material = new MaterialDto().create();
    }

    public void saveMaterial() {

    }

    public List<SelectItem> getUnits() {
        List<Unit> units = serviceFor(Unit.class).list();
        List<UnitDto> unitDtos = DTOs.marshalList(UnitDto.class, units);
        return UIHelper.selectItemsFromDict(unitDtos);

    }

    public void chooseMaterialCategory() {
        MaterialCategoryDto category = (MaterialCategoryDto)selectedMaterialCategoryNode.getData();
        material.setCategory(category);
    }

}
