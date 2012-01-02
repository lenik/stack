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
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitDto;

public class MaterialExAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String TREETABLE_CATEGORY = "mainForm:categoryTree";

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
        loadMaterialCategoryTree(null);
    }

    private void loadMaterialCategoryTree(Integer categoryId) {
        root = new DefaultTreeNode("categoryRoot", null);

        List<MaterialCategory> topCategories = serviceFor(
                MaterialCategory.class).list(//
                TreeCriteria.root());
        List<MaterialCategoryDto> topCategoryDtos = DTOs.mrefList(
                MaterialCategoryDto.class, ~MaterialCategoryDto.MATERIALS,
                topCategories);

        for (MaterialCategoryDto materialCategoryDto : topCategoryDtos) {
            loadMaterialCategoryRecursive(materialCategoryDto, root, categoryId);
        }
    }

    private void loadMaterialCategoryRecursive(MaterialCategoryDto materialCategoryDto, TreeNode parentTreeNode, Integer categoryId) {
        TreeNode materialCategoryNode = null;
        if (categoryId != null && categoryId.equals(materialCategoryDto.getId())) {
            //materialCategoryDto = reload(materialCategoryDto, MaterialCategoryDto.MATERIALS);
            materialCategoryNode = new DefaultTreeNode(materialCategoryDto, parentTreeNode);
            materialCategoryNode.setSelected(true);
            materials = materialCategoryDto.getMaterials();
        } else {
            materialCategoryNode = new DefaultTreeNode(materialCategoryDto, parentTreeNode);
        }

        List<MaterialCategoryDto> subMaterialCategories = materialCategoryDto.getChildren();
        if (subMaterialCategories != null) {
            for (MaterialCategoryDto subMaterialCategory : subMaterialCategories) {
                loadMaterialCategoryRecursive(subMaterialCategory, materialCategoryNode, categoryId);
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
        if (material.getCategory() == null || material.getCategory().getId() == null) {
            uiLogger.error("物料所属分类不能为空!");
            return;
        }

        if (
                material.getUnit() == null
                || material.getUnit().getId() == null
                || material.getUnit().getId().isEmpty()) {
            uiLogger.error("主单位不能为空!");
            return;
        }

        try {
            Material _m = material.unmarshal();

            serviceFor(Material.class).saveOrUpdate(_m);
            serviceFor(MaterialCategory.class).evict(material.getCategory().unmarshal());

            MaterialCategoryDto category = (MaterialCategoryDto)selectedMaterialCategoryNode.getData();
            loadMaterialCategoryTree(category.getId());

            uiLogger.info("保存物料成功.");
        } catch (Exception e) {
            uiLogger.error("保存物料出错!", e);
        }
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

    public void deleteMaterial() {
        try {
            serviceFor(Material.class).deleteById(material.getId());
            serviceFor(MaterialCategory.class).evict(material.getCategory().unmarshal());

            MaterialCategoryDto category = (MaterialCategoryDto)selectedMaterialCategoryNode.getData();
            loadMaterialCategoryTree(category.getId());

            uiLogger.info("删除物料成功.");

        } catch (Exception e) {
            uiLogger.error("删除物料出错!", e);
        }
    }
}
