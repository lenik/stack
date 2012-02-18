package com.bee32.sem.bom.web;

import java.math.BigDecimal;
import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.dto.PartItemDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.service.PartService;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.inventory.web.MaterialCategorySupportBean;
import com.bee32.sem.inventory.web.MaterialCategoryTreeModel;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(Part.class)
public class PartAdminBean
        extends MaterialCategorySupportBean {

    private static final long serialVersionUID = 1L;

    boolean productLike;
    MaterialDto selectedMaterial;
    BigDecimal calcPriceResult;

    public PartAdminBean() {
        super(Part.class, PartDto.class, 0);
    }

    @Override
    protected MaterialCategoryTreeModel createCategoryTree() {
        return new MaterialCategoryTreeModel(//
                MaterialCriteria.categoryType(//
                        MaterialType.PRODUCT, //
                        MaterialType.SEMI));
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        Integer categoryId = categoryTree.getSelectedId();
        // if (categoryId != null)
        if (categoryId == null) // select none if no category.
            categoryId = -1;
        elements.add(BomCriteria.targetCategory(categoryId));
    }

    @Override
    protected boolean postValidate(List<?> dtos) {
        for (Object dto : dtos) {
            PartDto part = (PartDto) dto;
            if (DTOs.isNull(part.getTarget())) {
                uiLogger.error("组件没有设置对应的物料");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (Part _part : uMap.<Part> entitySet()) {
            ctx.bean.getBean(PartService.class).changePartItemFromMaterialToPart(_part);
            PartDto partDto = uMap.getSourceDto(_part);
            if (partDto.isNewCreated())
                onCreatePart(partDto);
        }
    }

    @Override
    protected void postDelete(UnmarshalMap uMap) throws Exception {
        for (Part _part : uMap.<Part> entitySet()) {
            PartDto partDto = uMap.getSourceDto(_part);
            onDeletePart(partDto);
        }
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

    public void setPartMaterial() {
        PartDto part = getOpenedObject();
        // 检查此物料(成品)是否已经有bom存在
        List<Part> partList = ctx.data.access(Part.class).list(new Equals("target.id", selectedMaterial.getId()));

        if (partList != null & partList.size() > 0) {
            uiLogger.info("此物料已经存在BOM信息,继续新增的话,此物料将存在多个BOM信息");
            return;
        }
        part.setTarget(selectedMaterial);
        part.setCategory(selectedMaterial.getCategory());

        selectedMaterial = null;
    }

    public void setPartItemMaterial() {
        PartItemDto item = childrenMBean.getOpenedObject();
        List<Part> materialsIsPart = ctx.data.access(Part.class)
                .list(new Equals("target.id", selectedMaterial.getId()));
        if (materialsIsPart != null && materialsIsPart.size() > 0) {
            uiLogger.info("此物料是成品或半成品，已经存在BOM，请用[组件是半成品]标签页进行查找选择!!!");
            return;
        }
        item.setMaterial(selectedMaterial);
        //item.setPart(null);

        selectedMaterial = null;
    }

    public void calcPrice()
            throws FxrQueryException {
        PartDto part = getOpenedObject();
        if (part == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }
        Part _part = part.unmarshal();
        BigDecimal price = _part.calcPrice();
        if (price == null) {
            uiLogger.error("没有找到此产品的原材料原价格!");
            return;
        }
        calcPriceResult = price;
    }

    public BigDecimal getCalcPriceResult() {
        return calcPriceResult;
    }

    ListMBean<PartItemDto> childrenMBean = ListMBean.fromEL(this, "openedObject.children", PartItemDto.class);

    public ListMBean<PartItemDto> getChildrenMBean() {
        return childrenMBean;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    protected void onCreatePart(PartDto part) {
        Integer categoryId = part.getCategory().getId();
        MaterialCategoryDto category = categoryTree.getIndex().get(categoryId);
        if (category != null)
            category.setPartCount(category.getPartCount() + 1);
    }

    protected void onDeletePart(PartDto part) {
        Integer categoryId = part.getCategory().getId();
        MaterialCategoryDto category = categoryTree.getIndex().get(categoryId);
        if (category != null)
            category.setPartCount(category.getPartCount() - 1);
    }
}
