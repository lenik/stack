package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.hibernate.criterion.Restrictions;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialAttributeDto;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitDto;

@Component
@Scope("view")
public class MaterialViewBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    public static final String ATTR = "attr";

    private boolean searching;
    private String materialNamePattern;
    private List<MaterialDto> materialList;
    private TreeNode root;
    private TreeNode selectedMaterialNode;
    private MaterialDto tempMaterial = new MaterialDto().create();
    private UnitDto newUnit = new UnitDto().create();
    private MaterialAttributeDto tempAttr;

    MaterialViewBean() {
        root = new DefaultTreeNode("root", null);
        tempAttr = new MaterialAttributeDto(tempMaterial,"","");
    }

    @PostConstruct
    public void initialize() {
        buildTree();
    }

    public void doAddAttr(){
        tempMaterial.addAttribute(tempAttr);
    }

    public void doAddUhnit() {
        tempMaterial.setUnit(newUnit);
        Unit unit = newUnit.unmarshal();
        serviceFor(Unit.class).save(unit);
    }

    public void doAddMaterial(){
        Material material = tempMaterial.unmarshal();
        serviceFor(Material.class).saveOrUpdate(material);

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

    public void onNodeSelect(NodeSelectEvent event) {
        MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) selectedMaterialNode.getData();
        materialList = materialCategoryDto.getMaterials();
    }

    public List<SelectItem> getUnits() {
        List<Unit> unitList = serviceFor(Unit.class).list();
        List<UnitDto> unitDtoList = DTOs.marshalList(UnitDto.class, unitList);
        return UIHelper.selectItemsFromDict(unitDtoList);
    }

    public List<SelectItem> getCategories() {
        List<MaterialCategory> categoryList = serviceFor(MaterialCategory.class).list();
        List<MaterialCategoryDto> categoryDtoList = DTOs.marshalList(MaterialCategoryDto.class, 0, categoryList);
        List<SelectItem> itemList = new ArrayList<SelectItem>();
        for (MaterialCategoryDto materialCategoryDto : categoryDtoList)
            itemList.add(new SelectItem(materialCategoryDto.getId(), materialCategoryDto.getName()));
        return itemList;
    }

    public List<SelectItem> getStandardUnits() {
        List<Unit> unitList = serviceFor(Unit.class).list(//
                Restrictions.eq("scale", 0.0));
        List<UnitDto> unitDtoList = DTOs.marshalList(UnitDto.class, unitList);
        return UIHelper.selectItemsFromDict(unitDtoList);
    }

    public List<SelectItem> getUnitConvs(){
        List<UnitConv> unitConvList = serviceFor(UnitConv.class).list();
        List<UnitConvDto> unitConvDtoList = DTOs.marshalList(UnitConvDto.class, unitConvList);
        return UIHelper.selectItemsFromDict(unitConvDtoList);
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

    public List<MaterialDto> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialDto> materialList) {
        this.materialList = materialList;
    }

    public MaterialDto getTempMaterial() {
        return tempMaterial;
    }

    public void setTempMaterial(MaterialDto tempMaterial) {
        this.tempMaterial = tempMaterial;
    }

    public UnitDto getNewUnit() {
        return newUnit;
    }

    public void setNewUnit(UnitDto newUnit) {
        this.newUnit = newUnit;
    }

    public MaterialAttributeDto getTempAttr() {
        return tempAttr;
    }

    public void setTempAttr(MaterialAttributeDto tempAttr) {
        this.tempAttr = tempAttr;
    }

}
