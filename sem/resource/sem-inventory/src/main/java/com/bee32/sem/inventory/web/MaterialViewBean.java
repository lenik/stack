package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.MaterialAttributeDto;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.inventory.web.dialogs.StockLocationTreeDialogModel;
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
    public static final String CATEGORYDIALOG = "dialogForm:categoryDialog";

    private boolean searching;
    private String materialNamePattern;
    private List<MaterialDto> materialList;
    private TreeNode selectedMaterialNode;
    private TreeNode selectedMaterialCategory;
    private MaterialDto activeMaterial = new MaterialDto().create();
    private UnitDto newUnit = new UnitDto().create();
    private MaterialAttributeDto activeAttr;
    private MaterialPreferredLocationDto tempPreferredLocation;
    private MaterialWarehouseOptionDto option;
    private UnitConvDto tempUnitConv;
    private MaterialDto selectedMaterial;
    private MaterialCategoryDto activeCategory;

    // MaterialDialogModel activeMaterial;
    StockLocationTreeDialogModel stockLocationTreeDialog;
    MaterialCategoryTreeModel materialCategoryTree;

    public MaterialViewBean() {
        activeCategory = new MaterialCategoryDto().create();
        activeAttr = new MaterialAttributeDto(activeMaterial);
        option = new MaterialWarehouseOptionDto();
        option.setWarehouse(new StockWarehouseDto());
        tempPreferredLocation = new MaterialPreferredLocationDto();
        StockLocationDto location = new StockLocationDto();
        location.setWarehouse(new StockWarehouseDto());
        tempPreferredLocation.setLocation(location);

        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);
        materialCategoryTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialCategoryTree.addListner(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                MaterialCategory categoryEntity = serviceFor(MaterialCategory.class).load(materialCategoryDto.getId());
                materialCategoryDto = DTOs.marshal(MaterialCategoryDto.class, categoryEntity);
                materialList = materialCategoryDto.getMaterials();
            }
        });
        materialCategoryTree.addListner(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                activeMaterial.setCategory(materialCategoryDto);
            }
        });

        List<StockLocation> _rootLocations = serviceFor(StockLocation.class).list(TreeCriteria.root());
        List<StockLocationDto> rootLocations = DTOs.marshalList(StockLocationDto.class, _rootLocations);

        stockLocationTreeDialog = new StockLocationTreeDialogModel(rootLocations);
        stockLocationTreeDialog.addSelectListener(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                StockLocationDto selectedLocation = (StockLocationDto) event.getSelection();
                tempPreferredLocation.setLocation(selectedLocation);
            }
        });
    }

    public void createCategoryForm() {

        findComponentEx(CATEGORYDIALOG).setRendered(true);
    }

    public void editMaterialForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedMaterial == null) {
            context.addMessage(null, new FacesMessage("提示:", "请选择要编辑的物料!"));
            findComponentEx(MATERIALFOTM).setRendered(false);
            return;
        }
        activeMaterial = selectedMaterial;
    }

    public void createMaterialForm() {
        activeMaterial = new MaterialDto().create();
    }

    public void doAddAttr() {
        FacesContext context = FacesContext.getCurrentInstance();
        MaterialAttributeDto tattr = new MaterialAttributeDto(activeMaterial);
        tattr.setName(activeAttr.getName());
        tattr.setValue(activeAttr.getValue());

        if (tattr.getName().isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示:", "自定义属性名称不能为空!"));
            return;
        }
        if (tattr.getValue().isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示:", "自定义属性值不能为空!"));
            return;
        }
        activeMaterial.addAttribute(tattr);
    }

    public void doAddUhnit() {
        activeMaterial.setUnit(newUnit);
        Unit unit = newUnit.unmarshal();
        serviceFor(Unit.class).save(unit);
    }

    public void doAddOption() {
        MaterialWarehouseOptionDto mwod = new MaterialWarehouseOptionDto();
        mwod.setMaterial(activeMaterial);
        StockWarehouse sw = serviceFor(StockWarehouse.class).load(option.getWarehouse().getId());
        StockWarehouseDto swd = DTOs.mref(StockWarehouseDto.class, sw);
        mwod.setWarehouse(swd);
        mwod.setSafetyStock(option.getSafetyStock());
        mwod.setStkPeriod(option.getStkPeriod());
        activeMaterial.addOption(mwod);
    }

    public void doAddPreferredLocation() {
        MaterialPreferredLocationDto mpld = new MaterialPreferredLocationDto().create();
        mpld.setMaterial(activeMaterial);
        mpld.setLocation(tempPreferredLocation.getLocation());
        mpld.setDescription(tempPreferredLocation.getDescription());
        activeMaterial.addPreferredLocation(mpld);
    }

    public void doAddMaterial() {
        Material material = activeMaterial.unmarshal();
        serviceFor(Material.class).saveOrUpdate(material);
    }

    public void doAddMaterialCategory(){
        //TODO materialCategoryTree
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

    public List<SelectItem> getCodeGeneratories() {
        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem(CodeGenerator.GUID, "GUID"));
        items.add(new SelectItem(CodeGenerator.NONE, "NONE"));
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

    public MaterialDto getActiveMaterial() {
        return activeMaterial;
    }

    public void setActiveMaterial(MaterialDto activeMaterial) {
        this.activeMaterial = activeMaterial;
    }

    public UnitDto getNewUnit() {
        return newUnit;
    }

    public void setNewUnit(UnitDto newUnit) {
        this.newUnit = newUnit;
    }

    public MaterialAttributeDto getActiveAttr() {
        return activeAttr;
    }

    public void setActiveAttr(MaterialAttributeDto activeAttr) {
        this.activeAttr = activeAttr;
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

    public StockLocationTreeDialogModel getStockLocationTreeDialog() {
        return stockLocationTreeDialog;
    }

    public MaterialCategoryTreeModel getMaterialCategoryTree() {
        return materialCategoryTree;
    }

    public MaterialCategoryDto getActiveCategory() {
        return activeCategory;
    }

    public void setActiveCategory(MaterialCategoryDto activeCategory) {
        this.activeCategory = activeCategory;
    }
}
