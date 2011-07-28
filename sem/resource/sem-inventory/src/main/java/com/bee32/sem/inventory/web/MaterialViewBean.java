package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.MaterialAttributeDto;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.MaterialPriceDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.CodeGenerator;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.inventory.web.dialogs.StockLocationTreeDialogModel;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.CurrencyConfig;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.thing.ScaleItem;
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

    static final String CATEGORY_TREE = "main:categoryTree";
    static final String PANEL_INFO = "main:searchInfo";
    static final String BUTTON_PRESEARCH = "main:preSearchBtn";

    boolean editable;
    String materialPattern;
    List<MaterialDto> materialList;
    MaterialDto selectedMaterial;
    MaterialDto activeMaterial = new MaterialDto().create();
    UnitDto newUnit = new UnitDto().create();
    MaterialAttributeDto activeAttr;
    MaterialPreferredLocationDto activePreferredLocation;
    MaterialWarehouseOptionDto activeOption;
    UnitConvDto tempUnitConv;
    double value;
    String currencyCode;

    UnitConvDto activeUnitConv = new UnitConvDto().create();
    ScaleItem activeScaleItem;
    ScaleItem selectedScale;

    StockLocationTreeDialogModel stockLocationTreeDialog;
    MaterialCategoryTreeModel materialCategoryTree;
    MaterialCategoryTreeModel selectCategoryTree;

    private List<UnitConvDto> unitConvDtoList;

    public MaterialViewBean() {
        activeAttr = new MaterialAttributeDto().create();
        activeAttr.setMaterial(activeMaterial);

        currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
        activeOption = new MaterialWarehouseOptionDto().create();
        activePreferredLocation = new MaterialPreferredLocationDto().create();

        activeScaleItem = new ScaleItem();
        UnitDto unit = new UnitDto().create();
        activeScaleItem.setUnit(unit);

        initLocationTree();
        initMaterialCategoryTree();
        initSelectCategoryTree();
    }

    @PostConstruct
    public void initButton() {
        findComponent(PANEL_INFO).setRendered(false);
    }

    public void initLocationTree() {
        List<StockLocation> _rootLocations = serviceFor(StockLocation.class).list(TreeCriteria.root());
        List<StockLocationDto> rootLocations = DTOs.marshalList(StockLocationDto.class, _rootLocations, true);

        stockLocationTreeDialog = new StockLocationTreeDialogModel(rootLocations);
        stockLocationTreeDialog.addSelectListener(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                StockLocationDto selectedLocation = (StockLocationDto) event.getSelection();
                activePreferredLocation.setLocation(selectedLocation);
            }
        });
    }

    public void initSelectCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);
        selectCategoryTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        selectCategoryTree.addListener(new SelectionAdapter() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                if (materialCategoryDto != null) {
                    activeMaterial.setCategory(materialCategoryDto);
                }
            }

        });

    }

    public void initMaterialCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);

        materialCategoryTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialCategoryTree.addListener(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                List<Material> _materials = serviceFor(Material.class).list(//
                        // Order.asc("name"),
                        MaterialCriteria.categoryOf(materialCategoryDto.getId()));
                materialList = DTOs.marshalList(MaterialDto.class, _materials, true);
            }
        });
    }

    public void destroyMaterial() {
        try {
            long id = activeMaterial.getId();
            Material toDestroy = serviceFor(Material.class).getOrFail(id);
            serviceFor(Material.class).delete(toDestroy);
            materialList.remove(activeMaterial);
            uiLogger.info("删除物料成功");
        } catch (Exception e) {
            uiLogger.error("删除物料失败" + e.getMessage(), e);
        }
    }

    public void destroyAttribute() {
        activeMaterial.removeAttribute(activeAttr);
    }

    public void setMaterialDialogDisable() {
        editable = true;
    }

    public void setMaterialDialogEnable() {
        editable = false;
    }

    public void createMaterialForm() {
        editable = false;
        activeMaterial = new MaterialDto().create();
    }

    public void createPriceForm() {
        value = 0.0;
        currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    }

    public void createScaleItem() {
        activeScaleItem = new ScaleItem();
        UnitDto unitDto = new UnitDto().create();
        activeScaleItem.setUnit(unitDto);
    }

    public void doAddScaleItem() {
        String id = activeScaleItem.getUnit().getId();
        Unit _unit = serviceFor(Unit.class).getOrFail(id);
        UnitDto unit = DTOs.marshal(UnitDto.class, _unit);
        activeScaleItem.setUnit(unit);
        activeUnitConv.addScaleItem(activeScaleItem);
    }

    public void doSaveUnitConv() {
        try {
            UnitConv ucv = activeUnitConv.unmarshal();
            activeMaterial.setUnitConv(activeUnitConv);
            serviceFor(UnitConv.class).saveOrUpdate(ucv);
            uiLogger.info("保存单位还算表" + activeUnitConv.getLabel() + "成功");
            activeUnitConv = new UnitConvDto().create();
        } catch (Exception e) {
            uiLogger.error("保存单位还算表" + activeUnitConv.getLabel() + "失败:" + e.getMessage(), e);
        }
    }

    public void doAddMCValue() {
        MaterialPriceDto mpd = new MaterialPriceDto().create();
        mpd.setMaterial(activeMaterial);

        Currency currency = Currency.getInstance(currencyCode);
        MCValue mcv = new MCValue(currency, value);
        mpd.setPrice(mcv);

        activeMaterial.addPrice(mpd);
    }

    public void doAddAttr() {
        MaterialAttributeDto tattr = new MaterialAttributeDto().create();
        tattr.setMaterial(activeMaterial);
        tattr.setName(activeAttr.getName());
        tattr.setValue(activeAttr.getValue());

        if (tattr.getName().isEmpty()) {
            uiLogger.error("自定义属性名称不能为空!");
            return;
        }
        if (tattr.getValue().isEmpty()) {
            uiLogger.error("自定义属性值不能为空!");
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
        StockWarehouse sw = serviceFor(StockWarehouse.class).getOrFail(activeOption.getWarehouse().getId());
        StockWarehouseDto swd = DTOs.mref(StockWarehouseDto.class, sw);
        mwod.setWarehouse(swd);
        mwod.setSafetyStock(activeOption.getSafetyStock());
        mwod.setStkPeriod(activeOption.getStkPeriod());
        activeMaterial.addOption(mwod);
    }

    public void doAddPreferredLocation() {
        MaterialPreferredLocationDto mpld = new MaterialPreferredLocationDto().create();
        mpld.setMaterial(activeMaterial);
        mpld.setLocation(activePreferredLocation.getLocation());
        mpld.setDescription(activePreferredLocation.getDescription());
        activeMaterial.addPreferredLocation(mpld);
    }

    public void doAddMaterial() {
        String name = activeMaterial.getName();
        MaterialCategoryDto category = activeMaterial.getCategory();
        UnitDto unit = activeMaterial.getUnit();
        UnitConvDto unitConv = activeMaterial.getUnitConv();
        if (name == null || name.isEmpty()) {
            uiLogger.error("物料名称不能为空!");
            return;
        }
        if (category == null || category.isNull() || category.isNullRef()) {
            uiLogger.error("请选择物料分类!");
            return;
        }
        if (unit.isNullRef()) {
            uiLogger.error("请选择物料的基础单位!");
            return;
        }

        if (unitConv.getId() == null || unitConv.getId().isEmpty())
            unitConv = new UnitConvDto().ref();
        activeMaterial.setUnitConv(unitConv);

        try {
            Material materialEntity = activeMaterial.unmarshal();
            serviceFor(Material.class).saveOrUpdate(materialEntity);

//            activeMaterial = DTOs.marshal(MaterialDto.class, materialEntity);

            uiLogger.info("保存物料成功!");
        } catch (Exception e) {
            uiLogger.error("保存物料失败" + e.getMessage(), e);
        }

        if (materialList.contains(activeMaterial)) {
            int index = materialList.indexOf(activeMaterial);
            materialList.set(index, activeMaterial);
        } else {
            TreeNode currentNode = materialCategoryTree.getSelectedNode();
            TreeNode selectedNode = selectCategoryTree.getSelectedNode();
            if (currentNode != null && selectedNode != null) {
                MaterialCategoryDto currentBranch = (MaterialCategoryDto) currentNode.getData();
                MaterialCategoryDto selectedBranch = (MaterialCategoryDto) selectedNode.getData();
                if (currentBranch.getName().equals(selectedBranch.getName())) {
                    if (!materialList.contains(activeMaterial))
                        materialList.add(activeMaterial);
                }
            }
        }
    }

    public void searchForm() {
        findComponentEx(BUTTON_PRESEARCH).setEnabled(false);
        findComponent(CATEGORY_TREE).setRendered(false);
        findComponent(PANEL_INFO).setRendered(true);
    }

    public void doSearch() {
        if (!materialPattern.isEmpty() && materialPattern != null) {
            List<Material> _materials = serviceFor(Material.class).list(MaterialCriteria.namedLike(materialPattern));
            materialList = DTOs.marshalList(MaterialDto.class, _materials, true);
        }
    }

    public void resetSearch() {
        materialPattern = null;
        findComponent(CATEGORY_TREE).setRendered(true);
        findComponent(PANEL_INFO).setRendered(false);
        findComponentEx(BUTTON_PRESEARCH).setEnabled(true);
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
        String unitId = activeMaterial.getUnit().getId();
        if (StringUtils.isEmpty(unitId))
            unitConvDtoList = new ArrayList<UnitConvDto>();
        else {
            List<UnitConv> unitConvList = serviceFor(UnitConv.class).list(new Equals("unit.id", unitId));
            unitConvDtoList = DTOs.marshalList(UnitConvDto.class, unitConvList, true);
        }
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
        return UIHelper.selectItemsFromEnum(CodeGenerator.values());
    }

    public List<SelectItem> getCurrencies() {
        List<Currency> currencyList = CurrencyConfig.list();
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (Currency currency : currencyList) {
            SelectItem item = new SelectItem(currency.getCurrencyCode(), CurrencyConfig.format(currency));
            items.add(item);
        }
        return items;
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

    public String getActiveMaterialPrice() {
        MaterialPriceDto latestPrice = activeMaterial.getLatestPrice();
        if (latestPrice == null)
            return "(尚无价格)";
        else
            return latestPrice.getPrice().getValue().toString() + "(" + latestPrice.getPrice().getCurrencyCode() + ")";
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

    public MaterialWarehouseOptionDto getActiveOption() {
        return activeOption;
    }

    public void setActiveOption(MaterialWarehouseOptionDto option) {
        this.activeOption = option;
    }

    public MaterialPreferredLocationDto getActivePreferredLocation() {
        return activePreferredLocation;
    }

    public void setActivePreferredLocation(MaterialPreferredLocationDto activePreferredLocation) {
        this.activePreferredLocation = activePreferredLocation;
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
        this.activeMaterial = reload(selectedMaterial);
    }

    public StockLocationTreeDialogModel getStockLocationTreeDialog() {
        return stockLocationTreeDialog;
    }

    public MaterialCategoryTreeModel getMaterialCategoryTree() {
        return materialCategoryTree;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public MaterialCategoryTreeModel getSelectCategoryTree() {
        return selectCategoryTree;
    }

    public String getSelectedUnit() {
        return activeMaterial.getUnit().getId();
    }

    public void setSelectedUnit(String selectedUnit) {
        this.activeMaterial.getUnit().setId(selectedUnit);
        unitConvDtoList = null;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public UnitConvDto getActiveUnitConv() {
        return activeUnitConv;
    }

    public ScaleItem getActiveScaleItem() {
        return activeScaleItem;
    }

    public ScaleItem getSelectedScale() {
        return selectedScale;
    }

    public void setActiveUnitConv(UnitConvDto activeUnitConv) {
        this.activeUnitConv = activeUnitConv;
    }

    public void setActiveScaleItem(ScaleItem activeScaleItem) {
        this.activeScaleItem = activeScaleItem;
    }

    public void setSelectedScale(ScaleItem selectedScale) {
        this.selectedScale = selectedScale;
    }

}
