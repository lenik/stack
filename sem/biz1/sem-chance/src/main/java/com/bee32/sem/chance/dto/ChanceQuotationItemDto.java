package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.chance.entity.ChanceQuotationItem;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPriceDto;
import com.bee32.sem.world.thing.AbstractOrderItemDto;

public class ChanceQuotationItemDto
        extends AbstractOrderItemDto<ChanceQuotationItem> {

    private static final long serialVersionUID = 1L;

    private ChanceQuotationDto quotation;
    private MaterialDto material;
    private float discount;

    @Override
    protected void _marshal(ChanceQuotationItem source) {
        this.quotation = new ChanceQuotationDto().ref(source.getQuotation());
        this.material = mref(MaterialDto.class, source.getMaterial());
        this.discount = source.getDiscount();
    }

    @Override
    protected void _unmarshalTo(ChanceQuotationItem target) {
        merge(target, "quotation", quotation);
        merge(target, "material", material);
        target.setDiscount(discount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public ChanceQuotationDto getQuotation() {
        return quotation;
    }

    public void setQuotation(ChanceQuotationDto quotation) {
        this.quotation = quotation;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public MaterialPriceDto getBasePrice() {
        return material.getLatestPrice();
    }

}
