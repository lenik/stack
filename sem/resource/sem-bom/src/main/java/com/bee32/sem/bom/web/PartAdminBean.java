package com.bee32.sem.bom.web;

import java.math.BigDecimal;
import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.dto.PartItemDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.service.MaterialPriceNotFoundException;
import com.bee32.sem.bom.service.PartService;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.web.MaterialCategorySupportBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(Part.class)
public class PartAdminBean
        extends MaterialCategorySupportBean {

    private static final long serialVersionUID = 1L;

    boolean productLike;
    MaterialDto selectedMaterial;
    BigDecimal price;

    public PartAdminBean() {
        super(Part.class, PartDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        super.composeBaseCriteriaElements(elements);
        Integer categoryId = categoryTree.getSelectedId();
        // if (categoryId != null)
        if (categoryId == null) // select none if no category.
            categoryId = -1;
        elements.add(BomCriteria.listPartByCategory(categoryId));
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
    }

    public void confirmMaterial() {
        if (productLike) {
            PartDto part = getOpenedObject();
            // 检查此物料(成品)是否已经有bom存在
            List<Part> partList = serviceFor(Part.class).list(new Equals("target.id", selectedMaterial.getId()));

            if (partList != null & partList.size() > 0) {
                uiLogger.info("此物料已经存在BOM信息,继续新增的话,此物料将存在多个BOM信息");
                return;
            }
            part.setTarget(selectedMaterial);
        } else {
            PartItemDto item = itemsMBean.getOpenedObject();
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
        PartDto part = getOpenedObject();
        if (part == null) {
            uiLogger.error("请以单击选择需要计算价格的产品!");
            return;
        }
        try {
            Part _part = part.unmarshal(this);
            price = _part.calcPrice();
        } catch (MaterialPriceNotFoundException e) {
            uiLogger.error("没有找到此产品的原材料原价格!", e);
        }
    }

    ListMBean<PartItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.children", PartItemDto.class);

    public ListMBean<PartItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
