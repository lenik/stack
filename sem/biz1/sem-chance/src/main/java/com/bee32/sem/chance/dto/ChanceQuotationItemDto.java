package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.chance.entity.ChanceQutationItem;
import com.bee32.sem.world.thing.AbstractOrderItemDto;

public class ChanceQuotationItemDto
        extends AbstractOrderItemDto<ChanceQutationItem> {

    private static final long serialVersionUID = 1L;

    private ChanceQuotationDto quotation;
    private String material;
    private double discount;
    private BasePriceDto basePrice;

    @Override
    protected void _marshal(ChanceQutationItem source) {
        this.quotation = new ChanceQuotationDto().ref(source.getQuotation());
        this.material = source.getMaterial();
        this.discount = source.getDiscount();
        this.basePrice = mref(BasePriceDto.class, source.getBasePrice());
    }

    @Override
    protected void _unmarshalTo(ChanceQutationItem target) {
        merge(target, "quotation", quotation);
        merge(target, "basePrice", basePrice);
        target.setMaterial(material);
        target.setDiscount(discount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public ChanceQuotationDto getQuotation() {
        return quotation;
    }

    public void setQuotationInvoice(ChanceQuotationDto quotation) {
        this.quotation = quotation;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BasePriceDto getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BasePriceDto basePrice) {
        this.basePrice = basePrice;
    }

}
