package com.bee32.sem.bom.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Not;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.dto.PartItemDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.service.MaterialPriceNotFoundException;
import com.bee32.sem.bom.service.PartService;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.misc.DummyDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(Part.class)
public class PartAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    private PartItemDto item = DummyDto.create(PartItemDto.class);

    private PartDto selectedPart;
    private PartItemDto selectedItem;

    private boolean partOrComponent;

    private MaterialDto selectedMaterial;
    private PartDto selectedFindedPart;

    private BigDecimal price;

    private MaterialCategoryTreeModel categoryTree = new MaterialCategoryTreeModel();

    public PartAdminBean() {
        super(Part.class, PartDto.class, 0);
        initMaterialCategoryTree();
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        Integer categoryId = categoryTree.getSelectedId();
        // if (categoryId != null)
        if (categoryId == null) // select none if no category.
            categoryId = -1;
        elements.add(BomCriteria.listPartByCategory(categoryId));
    }

    public void initMaterialCategoryTree() {
        List<MaterialCategory> rootCategories = //
        serviceFor(MaterialCategory.class).list( //
                TreeCriteria.root(), //
                MaterialCriteria.classified(Classification.PRODUCT, Classification.SEMI));
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.mrefList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);

        categoryTree = new MaterialCategoryTreeModel(rootCategoryDtos);
    }

    public PartItemDto getItem() {
        return item;
    }

    public void setPart(PartItemDto item) {
        this.item = item;
    }

    public PartItemDto getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(PartItemDto selectedItem) {
        this.selectedItem = selectedItem;
    }

    public PartDto getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(PartDto selectedPart) {
        this.selectedPart = selectedPart;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public PartDto getSelectedFindedPart() {
        return selectedFindedPart;
    }

    public void setSelectedFindedPart(PartDto selectedFindedPart) {
        this.selectedFindedPart = selectedFindedPart;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MaterialCategoryTreeModel getMaterialCategoryTree() {
        return categoryTree;
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (PartDto part : uMap.<PartDto> dtos()) {
            if (part.getXrefCount() != 0) {
                uiLogger.error("此产品已经包含组件，必须先删除相应组件，才能删除本产品!");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (Part _part : uMap.<Part> entitySet()) {
            getBean(PartService.class).changePartItemFromMaterialToPart(_part);
        }

        if (categoryTree.getSelectedNode() != null) {
            MaterialCategoryDto c = (MaterialCategoryDto) categoryTree.getSelectedNode().getData();
            refreshPartCount(c.getId());
        }
    }

    public void refreshPart() {
        selectedPart = null;
        MaterialCategoryDto c = (MaterialCategoryDto) categoryTree.getSelectedNode().getData();
        refreshPartCount(c.getId());
    }

    public void showItemDetail() {
        if (selectedItem == null) {
            uiLogger.error("请以单击选择需要查看详细内容的BOM组件!");
            return;
        }

        item = selectedItem;
    }

    public void refreshItem() {
        selectedItem = null;
    }

    public List<PartItemDto> getItems() {
        if (part != null && part.getId() != null)
            return part.getChildren();
        else
            return new ArrayList<PartItemDto>();
    }

    public void findMaterial() {
        // String[] keyword = materialPattern.split(" ");
        //
        // for (int i = 0; i < keyword.length; i++) {
        // keyword[i] = keyword[i].trim();
        // }

        if (partOrComponent) {
            // 选择成品
            serviceFor(MaterialCategory.class).list(Or.of( //
                    new Equals("_classification", Classification.PRODUCT.getValue()), //
                    new Equals("_classification", Classification.SEMI.getValue()))); //
        } else {
            // 选择非成品(原材料，半成品，其他)
            serviceFor(MaterialCategory.class).list( //
                    Not.of(new Equals("_classification", Classification.PRODUCT.getValue())));
        }
    }

    public void chooseMaterial() {
        if (partOrComponent) {
            // 检查此物料(成品)是否已经有bom存在

            List<Part> partList = serviceFor(Part.class).list(new Equals("target.id", selectedMaterial.getId()));

            if (partList != null & partList.size() > 0) {
                uiLogger.info("此物料已经存在BOM信息,继续新增的话,此物料将存在多个BOM信息");
                return;
            }

            part.setTarget(selectedMaterial);
        } else {
            List<Part> materialsIsPart = serviceFor(Part.class).list(new Equals("target.id", selectedMaterial.getId()));
            if (materialsIsPart != null && materialsIsPart.size() > 0) {
                uiLogger.info("此物料是成品或半成品，已经存在BOM，请用[组件是半成品]标签页进行查找选择!!!");
                return;
            }
            item.setMaterial(selectedMaterial);
            item.setPart(null);
        }

        selectedMaterial = null;
    }

    public void calcPrice()
            throws FxrQueryException {

        if (selectedPart == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }

        try {
            Part _part = selectedPart.unmarshal(this);
            price = _part.calcPrice();
        } catch (MaterialPriceNotFoundException e) {
            uiLogger.error("没有找到此产品的原材料原价格!", e);
        }
    }

}
