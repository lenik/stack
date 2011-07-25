package com.bee32.sem.inventory.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.FlowEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;

@Component
@Scope("view")
public class MaterialSettingsBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;
    static final String BUTTON_CATEGORY_EDIT = "main:editCategory";
    static final String BUTTON_CATEGORY_DELETE = "main:deleteCategory";
    static final String BUTTON_UNIT_DELETE = "main:deleteUnit";
    static final String BUTTON_UNITCONV_EDIT = "main:editUnitConv";
    static final String BUTTON_UNITCONV_DELETE = "main:deleteUnitConv";

    MaterialCategoryDto activeCategory = new MaterialCategoryDto().create();

    MaterialCategoryTreeModel materialCategorySelectTree;
    MaterialCategoryTreeModel materialCategoryMainTree;

    public MaterialSettingsBean() {
        innitSelectCategoryTree();
        initMainTree();
    }

    @PostConstruct
    public void initPage() {
        findComponentEx(BUTTON_CATEGORY_EDIT).setEnabled(false);
        findComponentEx(BUTTON_CATEGORY_DELETE).setEnabled(false);
        findComponentEx(BUTTON_UNIT_DELETE).setEnabled(false);
        findComponentEx(BUTTON_UNITCONV_EDIT).setEnabled(false);
        findComponentEx(BUTTON_UNITCONV_DELETE).setEnabled(false);
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

// public List<SelectItem> getCodeGeneratories(){
// List<CodeGenerator> generatories = serviceFor(CodeGenerator.class).list();
// }

    public void onCategoryNodeSelect() {
        findComponentEx(BUTTON_CATEGORY_EDIT).setEnabled(true);
        findComponentEx(BUTTON_CATEGORY_DELETE).setEnabled(true);
    }

    public void onCategoryNodeUnselect() {
        findComponentEx(BUTTON_CATEGORY_EDIT).setEnabled(false);
        findComponentEx(BUTTON_CATEGORY_DELETE).setEnabled(false);
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public MaterialCategoryDto getActiveCategory() {
        return activeCategory;
    }

    public void setActiveCategory(MaterialCategoryDto activeCategory) {
        this.activeCategory = activeCategory;
    }

    public MaterialCategoryTreeModel getMaterialCategorySelectTree() {
        return materialCategorySelectTree;
    }

    public MaterialCategoryTreeModel getMaterialCategoryMainTree() {
        return materialCategoryMainTree;
    }

}
