package com.bee32.sem.chance.web;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.lang.Strings;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.stateflow.State;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceQuotationDto;
import com.bee32.sem.chance.dto.ChanceQuotationItemDto;
import com.bee32.sem.chance.entity.ChanceQuotation;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.MCValue;

public class ChanceQuotationBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    MaterialDto selectedMaterial;

    public ChanceQuotationBean() {
        super(ChanceQuotation.class, ChanceQuotationDto.class, 0);
    }

    void listQuotationByChance(ChanceDto chance) {
        List<ChanceQuotation> quotationList = serviceFor(ChanceQuotation.class).list(ChanceCriteria.chanceEquals(dto));
        quotations = UIHelper.selectable(DTOs.mrefList(ChanceQuotationDto.class, ChanceQuotationDto.ITEMS,
                quotationList));
    }

    public void confirmMaterial() {
        ChanceQuotationItemDto item = new ChanceQuotationItemDto().create();
        item.setParent(quotationCopy);
        item.setMaterial(materialDto);
        item.setDiscount(1f);
        quotationCopy.addItem(item);
    }

    public void calculatePriceChange() {
        MCValue _price = new MCValue(selectedQuotationItem.getPriceCurrency(), selectedQuotationItem.getViewPrice());
        selectedQuotationItem.setPrice(_price);
    }

    public void editQuotationItem() {
        BigDecimal value = new BigDecimal(selectedQuotationItem.getViewPrice());
        MCValue price = new MCValue(CurrencyConfig.getNative(), value);
        selectedQuotationItem.setPrice(price);
        quotationCopy.updateItem(selectedQuotationItem);
    }

    public void uneditQuotationItem() {
        state = State.EDIT;
    }

    public void saveQuotation() {
        if (quotationCopy.getItems() == null || quotationCopy.getItems().size() == 0) {
            uiLogger.error("错误提示:", "请添加明细");
            return;
        }

        if (Strings.isEmpty(quotationCopy.getSubject())) {
            uiLogger.error("错误提示:", "请添加报价单标题!");
            return;
        }

        try {
            ChanceQuotation quotationEntity = quotationCopy.unmarshal();
            serviceFor(ChanceQuotation.class).saveOrUpdate(quotationEntity);

            // if (quotationCopy.getId() == null)
            // quotations.add(DTOs.marshal(ChanceQuotationDto.class, quotationEntity));
            listQuotationByChance(dto);
            quotations.setSelection(null);

            uiLogger.info("提示", "保存报价单成功");

            state = State.EDIT;
        } catch (Exception e) {
            uiLogger.error("保存报价单错误", e);
        }
    }

}
