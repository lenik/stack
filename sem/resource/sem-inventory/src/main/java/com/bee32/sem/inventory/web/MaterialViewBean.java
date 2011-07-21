package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialAttributeDto;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitCriteria;
import com.bee32.sem.world.thing.UnitDto;

@Component
@Scope("view")
public class MaterialViewBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    public static final String ATTR = "attr";
    public static final String MATERIALFOTM = "material:materialDetail";

    private boolean searching;
    private String materialNamePattern;
    private List<MaterialDto> materialList;
// private TreeNode root;
    private TreeNode selectedMaterialNode;
    private TreeNode selectedMaterialCategory;
    private MaterialDto tempMaterial = new MaterialDto().create();
    private UnitDto newUnit = new UnitDto().create();
    private MaterialAttributeDto tempAttr;
    private MaterialPreferredLocationDto tempPreferredLocation;
    private MaterialWarehouseOptionDto option;
    private TreeNode locationTreeRoot;
    private TreeNode selectedStockLocation;
    private UnitConvDto tempUnitConv;
    private MaterialDto selectedMaterial;

    MaterialViewBean() {
// root = new DefaultTreeNode("root", null);
        locationTreeRoot = new DefaultTreeNode("root", null);
        tempAttr = new MaterialAttributeDto(tempMaterial, "", "");
        option = new MaterialWarehouseOptionDto();
        option.setWarehouse(new StockWarehouseDto());
        tempPreferredLocation = new MaterialPreferredLocationDto();
        StockLocationDto location = new StockLocationDto();
        location.setWarehouse(new StockWarehouseDto());
        tempPreferredLocation.setLocation(location);
    }

    @PostConstruct
    public void initialize() {
// buildCategoryTree();
        buildStockTree();
    }

    public void editMaterialForm(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(selectedMaterial == null){
            context.addMessage(null, new FacesMessage("提示:","请选择要编辑的物料!"));
            findComponentEx(MATERIALFOTM).setRendered(false);
            return;
        }
        tempMaterial = selectedMaterial;
    }

    public void createMaterialForm(){
        tempMaterial = new MaterialDto().create();
    }

    public void doAddAttr() {
        FacesContext context = FacesContext.getCurrentInstance();
        MaterialAttributeDto tattr = new MaterialAttributeDto(tempMaterial, tempAttr.getName(), tempAttr.getValue());
        if (tattr.getName().isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示:", "自定义属性名称不能为空!"));
            return;
        }
        if (tattr.getValue().isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示:", "自定义属性值不能为空!"));
            return;
        }
        tempMaterial.addAttribute(tattr);
    }

    public void doAddUhnit() {
        tempMaterial.setUnit(newUnit);
        Unit unit = newUnit.unmarshal();
        serviceFor(Unit.class).save(unit);
    }

    public void doAddOption() {
        MaterialWarehouseOptionDto mwod = new MaterialWarehouseOptionDto();
        mwod.setMaterial(tempMaterial);
        StockWarehouse sw = serviceFor(StockWarehouse.class).load(option.getWarehouse().getId());
        StockWarehouseDto swd = DTOs.mref(StockWarehouseDto.class, sw);
        mwod.setWarehouse(swd);
        mwod.setSafetyStock(option.getSafetyStock());
        mwod.setStkPeriod(option.getStkPeriod());
        tempMaterial.addOption(mwod);
    }

    public void doAddPreferredLocation() {
        MaterialPreferredLocationDto mpld = new MaterialPreferredLocationDto().create();
        mpld.setMaterial(tempMaterial);
        mpld.setLocation(tempPreferredLocation.getLocation());
        mpld.setDescription(tempPreferredLocation.getDescription());
        tempMaterial.addPreferredLocation(mpld);
    }

    public void doAddMaterial() {
        Material material = tempMaterial.unmarshal();
        serviceFor(Material.class).saveOrUpdate(material);
    }

    public void doSelectCategory() {
        MaterialCategoryDto category = (MaterialCategoryDto) selectedMaterialCategory.getData();
        tempMaterial.setCategory(category);
    }

    public void doSelectLocation() {
        StockLocationDto location = (StockLocationDto) selectedStockLocation.getData();
        tempPreferredLocation.setLocation(location);
    }

    public void buildCategoryTree(TreeNode parent) {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> materialCategoryDtoList = DTOs.marshalList(MaterialCategoryDto.class, //
                ~MaterialCategoryDto.MATERIALS, rootCategories);
        for (MaterialCategoryDto mc : materialCategoryDtoList)
            categoryTreeAssembler(mc, parent);
    }

    void categoryTreeAssembler(MaterialCategoryDto materialCategory, TreeNode parent) {
        TreeNode subParentNode = new DefaultTreeNode(materialCategory, parent);
        List<MaterialCategoryDto> childrenList = materialCategory.getChildren();
        if (childrenList != null)
            for (MaterialCategoryDto mc : childrenList)
                categoryTreeAssembler(mc, subParentNode);
    }

    public void buildStockTree() {
        List<StockLocation> stockLocationList = serviceFor(StockLocation.class).list(//
                TreeCriteria.root());
        List<StockLocationDto> stockLocationDtoList = DTOs.marshalList(StockLocationDto.class, stockLocationList);
        Map<String, TreeNode> locationMap = new HashMap<String, TreeNode>();

        for (StockLocationDto location : stockLocationDtoList) {
            Set<String> warehouseNameSet = locationMap.keySet();
            if (!warehouseNameSet.contains(location.getWarehouse().getName())) {
                location.setNodeName(location.getWarehouse().getName());
                TreeNode treeNode = new DefaultTreeNode(location, locationTreeRoot);
                locationMap.put(location.getWarehouse().getName(), treeNode);
            }
        }

        for (StockLocationDto location : stockLocationDtoList) {
            TreeNode warehouseNode = locationMap.get(location.getWarehouse().getName());
            StockLocationDto firstLocationLayer = new StockLocationDto();
            firstLocationLayer.setWarehouse(location.getWarehouse());
            firstLocationLayer.setNodeName(location.getAddress());
            firstLocationLayer.setAddress(location.getAddress());
            firstLocationLayer.setX(location.getX());
            firstLocationLayer.setY(location.getY());
            firstLocationLayer.setZ(location.getZ());
            firstLocationLayer.setChildren(location.getChildren());
            stockLocationTreeAssembler(firstLocationLayer, warehouseNode);
        }

    }

    void stockLocationTreeAssembler(StockLocationDto sld, TreeNode node) {
        TreeNode temp = new DefaultTreeNode(sld, node);
        List<StockLocationDto> childrenList = sld.getChildren();
        if (childrenList != null)
            for (StockLocationDto children : childrenList)
                stockLocationTreeAssembler(children, temp);
    }

    public void onNodeSelect(NodeSelectEvent event) {
        MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) selectedMaterialNode.getData();
        MaterialCategory categoryEntity = serviceFor(MaterialCategory.class).load(materialCategoryDto.getId());
        materialCategoryDto = DTOs.marshal(MaterialCategoryDto.class, categoryEntity);
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
        List<Unit> unitList = serviceFor(Unit.class).list(UnitCriteria.standardUnits);
        List<UnitDto> unitDtoList = DTOs.marshalList(UnitDto.class, unitList);
        return UIHelper.selectItemsFromDict(unitDtoList);
    }

    public List<SelectItem> getUnitConvs() {
        List<UnitConv> unitConvList = serviceFor(UnitConv.class).list();
        List<UnitConvDto> unitConvDtoList = DTOs.marshalList(UnitConvDto.class, unitConvList);
        return UIHelper.selectItemsFromDict(unitConvDtoList);
    }

    public List<SelectItem> getWarehouses() {
        List<StockWarehouse> warehouseList = serviceFor(StockWarehouse.class).list();
        List<StockWarehouseDto> warehouseDtoList = DTOs.marshalList(StockWarehouseDto.class, warehouseList);
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (StockWarehouseDto swd : warehouseDtoList) {
            SelectItem item = new SelectItem(swd.getId(), swd.getName());
            items.add(item);
        }
        return items;
    }

    public TreeNode getRoot() {
        TreeNode parent = new DefaultTreeNode("root", null);
        buildCategoryTree(parent);
        return parent;
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

// public TreeNode getRoot() {
// return root;
// }
//
// public void setRoot(TreeNode root) {
// this.root = root;
// }

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

    public MaterialWarehouseOptionDto getOption() {
        return option;
    }

    public void setOption(MaterialWarehouseOptionDto option) {
        this.option = option;
    }

    public MaterialPreferredLocationDto getTempPreferredLocation() {
        return tempPreferredLocation;
    }

    public void setTempPreferredLocation(MaterialPreferredLocationDto tempLocation) {
        this.tempPreferredLocation = tempLocation;
    }

    public TreeNode getSelectedMaterialCategory() {
        return selectedMaterialCategory;
    }

    public void setSelectedMaterialCategory(TreeNode selectedMaterialCategory) {
        this.selectedMaterialCategory = selectedMaterialCategory;
    }

    public TreeNode getLocationTreeRoot() {
        return locationTreeRoot;
    }

    public void setLocationTreeRoot(TreeNode locationTreeRoot) {
        this.locationTreeRoot = locationTreeRoot;
    }

    public TreeNode getSelectedStockLocation() {
        return selectedStockLocation;
    }

    public void setSelectedStockLocation(TreeNode selectedStockLocation) {
        this.selectedStockLocation = selectedStockLocation;
    }

    public UnitConvDto getTempUnitConv() {
        return tempUnitConv;
    }

    public void setTempUnitConv(UnitConvDto tempUnitConv) {
        this.tempUnitConv = tempUnitConv;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

}
