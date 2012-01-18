package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import org.primefaces.event.FlowEvent;
import org.zkoss.lang.Strings;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntities;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPriceDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.thing.ScaleItem;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitCriteria;
import com.bee32.sem.world.thing.UnitDto;

@ForEntities({
//
        @ForEntity(MaterialCategory.class), //
        @ForEntity(Unit.class), //
        @ForEntity(UnitConv.class), })
public class MaterialSettingsBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;
    static final String BUTTON_CATEGORY_EDIT = "main:editCategory";
    static final String BUTTON_CATEGORY_DELETE = "main:deleteCategory";
    static final String TREE_MAIN = "main:mainTree";

    boolean unitConvEditable;
// boolean categoryOptionable;
    boolean cantEdit;

    MaterialCategoryDto activeCategory = new MaterialCategoryDto().create();
    List<UnitDto> unitList;
    UnitDto activeUnit = new UnitDto().create();
    UnitDto selectedUnit;

    List<UnitConvDto> unitConvList;
    UnitConvDto activeUnitConv = new UnitConvDto().create();
    UnitConvDto selectedUnitConv;

    ScaleItem activeScaleItem;
    ScaleItem selectedScale;

    double value;
    String currencyCode;

    List<MaterialDto> materialList;
    MaterialDto activeMaterial = new MaterialDto().create();

    MaterialCategoryTreeModel materialCategorySelectTree;
    MaterialCategoryTreeModel materialCategoryMainTree;
    MaterialCategoryTreeModel materialPriceTree;

    public MaterialSettingsBean() {
        activeScaleItem = new ScaleItem();
        UnitDto unit = new UnitDto().create();
        activeScaleItem.setUnit(unit);

        currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

        initSelectCategoryTree();
        initMainTree();
        initMaterialPriceTree();
        initUnitList();
        initUnitConvList();
    }

    public void initMainTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(//
                TreeCriteria.root(), //
                Order.asc("name"));
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.mrefList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);
        materialCategoryMainTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialCategoryMainTree.setSelectedNode(null);
        // XXX to add listener if necessary
    }

    public void initSelectCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(//
                TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.mrefList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);
        materialCategorySelectTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialCategorySelectTree.addListener(new SelectionAdapter() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                if (materialCategoryDto != null) {
                    activeCategory.setParent(materialCategoryDto);
                }
            }

        });
    }

    public void initMaterialPriceTree() {
        List<MaterialCategory> roots = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, roots);
        materialPriceTree = new MaterialCategoryTreeModel(rootDtos);
        materialPriceTree.addListener(new SelectionAdapter() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto cad = (MaterialCategoryDto) event.getSelection();
                List<Material> materials = serviceFor(Material.class).list(//
                        MaterialCriteria.categoryOf(cad.getId()));
                materialList = DTOs.marshalList(MaterialDto.class, MaterialDto.PRICES, materials);
            }

        });
    }

    public void refreshCategorySelectTree() {
        initSelectCategoryTree();
    }

    public void initUnitConvList() {
        List<UnitConv> ucl = serviceFor(UnitConv.class).list();
        unitConvList = DTOs.marshalList(UnitConvDto.class, ucl);
    }

    public void setUnitConvUneditable() {
        unitConvEditable = true;
    }

    public void createScaleItem() {
        activeScaleItem = new ScaleItem();
        UnitDto unitDto = new UnitDto().create();
        activeScaleItem.setUnit(unitDto);
    }

    public void createUnitConvForm() {
        unitConvEditable = false;
        cantEdit = false;
        activeUnitConv = new UnitConvDto().create();
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
            serviceFor(UnitConv.class).saveOrUpdate(ucv);
            initUnitConvList();
            uiLogger.info("保存单位换算表成功");
        } catch (Exception e) {
            uiLogger.error("保存单位换算表失败", e);
        }
    }

    public void destroyUnitConv() {
        Long convId = selectedUnitConv.getId();
        if (convId == null) {
            uiLogger.error("没有指定单位换算表");
            return;
        }
        try {
            UnitConv uc = serviceFor(UnitConv.class).getOrFail(convId);
            serviceFor(UnitConv.class).delete(uc);
            initUnitConvList();
            uiLogger.info("删除单位换算表成功");
        } catch (Exception e) {
            uiLogger.error("删除单位换算表失败", e);
        }
    }

    public void initUnitList() {
        List<Unit> ul = serviceFor(Unit.class).list();
        unitList = DTOs.marshalList(UnitDto.class, ul);
    }

    public void createUnitForm() {
        cantEdit = false;
        activeUnit = new UnitDto().create();
    }

    public void doSaveUnit() {
        String stuId = activeUnit.getStdUnit().getId();
        UnitDto stdUnit = null;
        if (!Strings.isEmpty(stuId))
            stdUnit = DTOs.marshal(UnitDto.class, serviceFor(Unit.class).getOrFail(//
                    activeUnit.getStdUnit().getId()));

        try {
            activeUnit.setStdUnit(stdUnit);
            Unit enity = activeUnit.unmarshal();
            serviceFor(Unit.class).saveOrUpdate(enity);
            initUnitList();
            uiLogger.info("保存单位成功!");
        } catch (Exception e) {
            uiLogger.error("保存单位错误", e);
        }
    }

    public void destroyUnit() {
        try {
            String unitId = selectedUnit.getId();
            Unit unitl = serviceFor(Unit.class).getOrFail(unitId);
            serviceFor(Unit.class).delete(unitl);
            unitList.remove(selectedUnit);
            // initUnitList();
            uiLogger.info("删除单位成功");
        } catch (Exception e) {
            uiLogger.error("删除单位失败", e);
        }
    }

    public void createCategoryForm() {
        activeCategory = new MaterialCategoryDto().create();
    }

    public void addSubCategory() {
        activeCategory = new MaterialCategoryDto().create();
        activeCategory.setParent((MaterialCategoryDto) materialCategoryMainTree.getSelectedNode().getData());
    }

    public void editCategoryForm() {
        activeCategory = (MaterialCategoryDto) materialCategoryMainTree.getSelectedNode().getData();
        activeCategory = reload(activeCategory);
    }

    public void updatePrice() {
        List<MaterialPrice> prices = activeMaterial.unmarshal().getPrices();
        try {
            Material material_entity = serviceFor(Material.class).getOrFail(activeMaterial.getId());
            material_entity.setPrices(prices);
            serviceFor(Material.class).saveOrUpdate(material_entity);
            uiLogger.info("更新价格成功!");
        } catch (Exception e) {
            uiLogger.error("更新价格失败", e);
        }
    }

    public void doSaveCategory() {
        try {
            MaterialCategory subCategory = activeCategory.unmarshal();
            serviceFor(MaterialCategory.class).saveOrUpdate(subCategory);

            if (subCategory.getParent() != null) {
                subCategory.getParent().addChild(subCategory);
            }

            initMainTree();
            initSelectCategoryTree();
            uiLogger.info("保存物料分类成功!");
        } catch (Exception e) {
            uiLogger.error("保存物料失败", e);
        }
    }

    public void destroyCategory() {
        MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) materialCategoryMainTree.getSelectedNode()
                .getData();
        Integer id = materialCategoryDto.getId();
        try {
            MaterialCategory entity = serviceFor(MaterialCategory.class).getOrFail(id);
            serviceFor(MaterialCategory.class).delete(entity);
            initMainTree();
            initSelectCategoryTree();
            uiLogger.info("删除物料分类成功!");
        } catch (Exception e) {
            uiLogger.error("删除物料分类失败", e);
        }
    }

// public void onCategoryNodeSelect() {
// categoryOptionable = true;
// }

// public void onCategoryNodeUnselect() {
// categoryOptionable = false;
// }

    public void setDictNameCantEdit() {
        cantEdit = true;
        unitConvEditable = false;
    }

    public void doAddMCValue() {
        MaterialPriceDto mpd = new MaterialPriceDto().create();
        mpd.setMaterial(activeMaterial);

        Currency currency = Currency.getInstance(currencyCode);
        MCValue mcv = new MCValue(currency, value);
        mpd.setPrice(mcv);

        activeMaterial.addPrice(mpd);
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public List<SelectItem> getStandardUnits() {
        List<Unit> stdUnitList = serviceFor(Unit.class).list(UnitCriteria.standardUnits);
        List<UnitDto> stdUnitDtoList = DTOs.marshalList(UnitDto.class, stdUnitList);
        return UIHelper.selectItemsFromDict(stdUnitDtoList);
    }

    public List<SelectItem> getUnits() {
        List<Unit> unitList = serviceFor(Unit.class).list();
        List<UnitDto> unitDtoList = DTOs.marshalList(UnitDto.class, unitList);
        return UIHelper.selectItemsFromDict(unitDtoList);
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

    public MaterialCategoryDto getActiveCategory() {
        return activeCategory;
    }

    public void setActiveCategory(MaterialCategoryDto activeCategory) {
        this.activeCategory = activeCategory;
    }

    public List<UnitDto> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<UnitDto> unitList) {
        this.unitList = unitList;
    }

    public UnitDto getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(UnitDto activeUnit) {
        this.activeUnit = activeUnit;
    }

    public UnitDto getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(UnitDto sUnit) {
        this.selectedUnit = sUnit;
        this.activeUnit = reload(selectedUnit);
    }

    public List<UnitConvDto> getUnitConvList() {
        return unitConvList;
    }

    public void setUnitConvList(List<UnitConvDto> unitConvList) {
        this.unitConvList = unitConvList;
    }

    public UnitConvDto getActiveUnitConv() {
        return activeUnitConv;
    }

    public void setActiveUnitConv(UnitConvDto activeUnitConv) {
        this.activeUnitConv = activeUnitConv;
    }

    public UnitConvDto getSelectedUnitConv() {
        return selectedUnitConv;
    }

    public void setSelectedUnitConv(UnitConvDto selectedUnitConv) {
        this.selectedUnitConv = selectedUnitConv;
        this.activeUnitConv = reload(selectedUnitConv);
    }

    public MaterialCategoryTreeModel getMaterialCategorySelectTree() {
        return materialCategorySelectTree;
    }

    public MaterialCategoryTreeModel getMaterialCategoryMainTree() {
        return materialCategoryMainTree;
    }

    public MaterialCategoryTreeModel getMaterialPriceTree() {
        return materialPriceTree;
    }

    public ScaleItem getActiveScaleItem() {
        return activeScaleItem;
    }

    public void setActiveScaleItem(ScaleItem activeScaleItem) {
        this.activeScaleItem = activeScaleItem;
    }

    public ScaleItem getSelectedScale() {
        return selectedScale;
    }

    public void setSelectedScale(ScaleItem selectedScale) {
        this.selectedScale = selectedScale;
        activeScaleItem = new ScaleItem();
        activeScaleItem.setScale(selectedScale.getScale());
        UnitDto unit = reload(selectedScale.getUnit());
        activeScaleItem.setUnit(unit);
    }

    public boolean isUnitConvEditable() {
        return unitConvEditable;
    }

    public void setUnitConvEditable(boolean unitConvEditable) {
        this.unitConvEditable = unitConvEditable;
    }

// public boolean isCategoryOptionable() {
// return categoryOptionable;
// }
//
// public void setCategoryOptionable(boolean categoryOptionable) {
// this.categoryOptionable = categoryOptionable;
// }

    public boolean isCantEdit() {
        return cantEdit;
    }

    public void setCantEdit(boolean cantEdit) {
        this.cantEdit = cantEdit;
    }

    public List<MaterialDto> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialDto> materialList) {
        this.materialList = materialList;
    }

    public MaterialDto getActiveMaterial() {
        if(activeMaterial == null)
            activeMaterial = new MaterialDto().create();
        return activeMaterial;
    }

    public void setActiveMaterial(MaterialDto activeMaterial) {
        this.activeMaterial = activeMaterial;
    }

    public double getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<SelectItem> getClassifications() {
        List<SelectItem> classifications = new ArrayList<SelectItem>();
        for (Classification c : Classification.values())
            classifications.add(new SelectItem(c.getValue(), c.getDisplayName()));
        return classifications;
    }

}
