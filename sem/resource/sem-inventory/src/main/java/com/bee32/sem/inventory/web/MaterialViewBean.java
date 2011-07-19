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
    private TreeNode selectedMaterialCategory;
    private MaterialDto tempMaterial = new MaterialDto().create();
    private UnitDto newUnit = new UnitDto().create();
    private MaterialAttributeDto tempAttr;
    private MaterialPreferredLocationDto tempLocation;
    private MaterialWarehouseOptionDto option;
    private TreeNode locationTreeRoot;

    MaterialViewBean() {
        root = new DefaultTreeNode("root", null);
        locationTreeRoot = new DefaultTreeNode("root", null);
        tempAttr = new MaterialAttributeDto(tempMaterial, "", "");
        option = new MaterialWarehouseOptionDto();
        option.setWarehouse(new StockWarehouseDto());
        tempLocation = new MaterialPreferredLocationDto();
        StockLocationDto location = new StockLocationDto();
        location.setWarehouse(new StockWarehouseDto());
        tempLocation.setLocation(location);
    }

    @PostConstruct
    public void initialize() {
        buildCategoryTree();
    }

    public void doAddAttr() {
        MaterialAttributeDto tattr = new MaterialAttributeDto(tempMaterial, tempAttr.getName(), tempAttr.getValue());
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
        MaterialPreferredLocationDto mpld = new MaterialPreferredLocationDto();
        mpld.setMaterial(tempMaterial);

        StockWarehouse sw = serviceFor(StockWarehouse.class).load(tempLocation.getLocation().getWarehouse().getId());
        StockWarehouseDto swd = DTOs.mref(StockWarehouseDto.class, sw);
        double x = tempLocation.getLocation().getX();
        double y = tempLocation.getLocation().getY();
        double z = tempLocation.getLocation().getZ();
        String address = tempLocation.getLocation().getAddress();
        String desc = tempLocation.getDescription();
        StockLocationDto sld = new StockLocationDto();
        sld.setWarehouse(swd);
        sld.setX(x);
        sld.setY(y);
        sld.setZ(z);
        sld.setAddress(address);
        mpld.setLocation(sld);
        mpld.setDescription(desc);
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

    public void buildCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(Restrictions.isNull("parent"));
        List<MaterialCategoryDto> materialCategoryDtoList = DTOs.marshalList(MaterialCategoryDto.class, rootCategories);
        for (MaterialCategoryDto mc : materialCategoryDtoList)
            treeAssembling(mc, root);
    }

    void treeAssembling(MaterialCategoryDto materialCategory, TreeNode parent) {
        TreeNode subParentNode = new DefaultTreeNode(materialCategory, parent);
        List<MaterialCategoryDto> childrenList = materialCategory.getChildren();
        if (childrenList != null)
            for (MaterialCategoryDto mc : childrenList)
                treeAssembling(mc, subParentNode);
    }

    public void buildStockTree(){
        List<StockLocation> stockLocationList = serviceFor(StockLocation.class).list(//
                Restrictions.isNull("parent"));
        List<StockLocationDto> stockLocationDtoList = DTOs.marshalList(StockLocationDto.class, stockLocationList);
        //FIXME
        for(StockLocationDto sld : stockLocationDtoList){

        }
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
            SelectItem item = new SelectItem(swd.getId(), swd.getAddress());
            items.add(item);
        }
        return items;
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

    public MaterialWarehouseOptionDto getOption() {
        return option;
    }

    public void setOption(MaterialWarehouseOptionDto option) {
        this.option = option;
    }

    public MaterialPreferredLocationDto getTempLocation() {
        return tempLocation;
    }

    public void setTempLocation(MaterialPreferredLocationDto tempLocation) {
        this.tempLocation = tempLocation;
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

}
