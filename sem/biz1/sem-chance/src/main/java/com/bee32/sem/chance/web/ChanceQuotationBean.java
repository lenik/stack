package com.bee32.sem.chance.web;

import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceQuotationDto;
import com.bee32.sem.chance.dto.ChanceQuotationItemDto;
import com.bee32.sem.chance.entity.ChanceQuotation;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChanceQuotationBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceDto chance;
    MaterialDto selectedMaterial;
    ChanceQuotationItemDto selectedItem;

    public ChanceQuotationBean() {
        super(ChanceQuotation.class, ChanceQuotationDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (chance != null)
            elements.add(new Equals("chance.id", chance.getId()));
    }

    @Override
    protected boolean postValidate(List<?> dtos) {
        for (Object dto : dtos) {
            ChanceQuotationDto quotation = (ChanceQuotationDto) dto;
            if (quotation.getItems() == null || quotation.getItems().size() == 0) {
                uiLogger.error("错误提示:", "请添加明细");
                return false;
            }
        }
        return true;
    }

    public void addMaterial() {
        ChanceQuotationDto quotation = getOpenedObject();
        ChanceQuotationItemDto item = new ChanceQuotationItemDto().create();
        item.setParent(quotation);
        item.setMaterial(selectedMaterial);
        item.setDiscount(1f);
        quotation.addItem(item);
    }

    public void editQuotationItem() {
        ChanceQuotationDto quotation = getOpenedObject();
        quotation.invalidateTotal();
    }

}
