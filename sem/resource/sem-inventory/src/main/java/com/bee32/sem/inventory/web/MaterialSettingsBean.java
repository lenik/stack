package com.bee32.sem.inventory.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.event.FlowEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.lang.Strings;

import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.ScaleItem;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitConv;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitCriteria;
import com.bee32.sem.world.thing.UnitDto;

@Component
@Scope("view")
public class MaterialSettingsBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;
    static final String BUTTON_CATEGORY_EDIT = "main:editCategory";
    static final String BUTTON_CATEGORY_DELETE = "main:deleteCategory";

    boolean unitConvEditable;
    boolean categoryOptionable;
    boolean cantEdit;

    MaterialCategoryDto activeCategory = new MaterialCategoryDto().create();
    List<UnitDto> unitList;
    UnitDto activeUnit = new UnitDto().create();;
    UnitDto selectedUnit;

    List<UnitConvDto> unitConvList;
    UnitConvDto activeUnitConv = new UnitConvDto().create();
    UnitConvDto selectedUnitConv;

    ScaleItem activeScaleItem;
    ScaleItem selectedScale;

    MaterialCategoryTreeModel materialCategorySelectTree;
    MaterialCategoryTreeModel materialCategoryMainTree;

    public MaterialSettingsBean() {
        activeScaleItem = new ScaleItem();
        UnitDto unit = new UnitDto().create();
        activeScaleItem.setUnit(unit);

        innitSelectCategoryTree();
        initMainTree();
        initUnitList();
        initUnitConvList();
    }

    @PostConstruct
    public void initPage() {
    }

    public void initMainTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);
        materialCategoryMainTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialCategoryMainTree.addListener(new SelectionAdapter() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
            }

        });
    }

    public void innitSelectCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
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

//    public void editUnitConvForm() {
//        activeUnitConv = selectedUnitConv;
//        activeUnitConv = reload(activeUnitConv);
//    }

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
            uiLogger.info("保存单位还算表" + activeUnitConv.getLabel() + "成功");
        } catch (Exception e) {
            uiLogger.error("保存单位还算表" + activeUnitConv.getLabel() + "失败:" + e.getMessage(), e);
        }
    }

    public void destroyUnitConv() {
        try {
            String convId = selectedUnitConv.getId();
            UnitConv uc = serviceFor(UnitConv.class).getOrFail(convId);
            serviceFor(UnitConv.class).delete(uc);
            initUnitConvList();
            uiLogger.info("删除单位还算表" + selectedUnitConv.getLabel() + "成功");
        } catch (Exception e) {
            uiLogger.error("删除单位还算表" + selectedUnitConv.getLabel() + "失败" + e.getMessage(), e);
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

//    public void editUnitForm() {
//        activeUnit = selectedUnit;
//        activeUnit = reload(activeUnit);
//    }

    public void doSaveUnit() {
        String stuId = activeUnit.getStdUnit().getId();
        UnitDto stdUnit = null;
        if (!Strings.isEmpty(stuId))
            stdUnit = DTOs.marshal(UnitDto.class, serviceFor(Unit.class).getOrFail(activeUnit.getStdUnit().getId()));

        try {
            activeUnit.setStdUnit(stdUnit);
            Unit enity = activeUnit.unmarshal();
            serviceFor(Unit.class).saveOrUpdate(enity);
            initUnitList();
            uiLogger.info("保存单位" + activeUnit.getLabel() + "成功!");
        } catch (Exception e) {
            uiLogger.error("保存单位" + activeUnit.getLabel() + "错误:" + e.getMessage(), e);
        }
    }

    public void destroyUnit() {
        try {
            String unitId = selectedUnit.getId();
            Unit unitl = serviceFor(Unit.class).getOrFail(unitId);
            serviceFor(Unit.class).delete(unitl);
            initUnitList();
            uiLogger.info("删除单位" + selectedUnit.getLabel() + "成功");
        } catch (Exception e) {
            uiLogger.error("删除单位" + selectedUnit.getLabel() + "失败:" + e.getMessage(), e);
        }
    }

    public void createCategoryForm() {
        activeCategory = new MaterialCategoryDto().create();
    }

    public void editCategoryForm() {
        activeCategory = (MaterialCategoryDto) materialCategoryMainTree.getSelectedNode().getData();
        activeCategory = reload(activeCategory);
    }

    public void doSaveCategory() {
        try {
            MaterialCategory category = activeCategory.unmarshal();
            serviceFor(MaterialCategory.class).saveOrUpdate(category);
            initMainTree();
            innitSelectCategoryTree();
            uiLogger.info("保存物料分类:" + activeCategory.getName() + "成功!");
        } catch (Exception e) {
            uiLogger.error("保存物料失败:" + e.getMessage(), e);
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
            innitSelectCategoryTree();
            uiLogger.info("删除物料分类成功!");
        } catch (Exception e) {
            uiLogger.error("删除物料分类成功!" + e.getMessage(), e);
        }
    }

    public void onCategoryNodeSelect() {
        categoryOptionable = true;
    }

    public void onCategoryNodeUnselect() {
        categoryOptionable = false;
    }

    public void setDictNameCantEdit() {
        cantEdit = true;
        unitConvEditable = false;
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

    public boolean isCategoryOptionable() {
        return categoryOptionable;
    }

    public void setCategoryOptionable(boolean categoryOptionable) {
        this.categoryOptionable = categoryOptionable;
    }

    public boolean isCantEdit() {
        return cantEdit;
    }

    public void setCantEdit(boolean cantEdit) {
        this.cantEdit = cantEdit;
    }

}
