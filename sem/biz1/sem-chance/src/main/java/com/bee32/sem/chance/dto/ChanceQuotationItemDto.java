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

    ChanceQuotationDto parent;
    MaterialDto material;
    float discount;
    double viewPrice = 0.0;

    @Override
    protected void _marshal(ChanceQuotationItem source) {
        this.parent = new ChanceQuotationDto().ref(source.getParent());
        this.material = mref(MaterialDto.class, MaterialDto.PRICES, source.getMaterial());
        this.discount = source.getDiscount();
        this.viewPrice = getPrice().getValue().doubleValue();
    }

    @Override
    protected void _unmarshalTo(ChanceQuotationItem target) {
        merge(target, "parent", parent);
        merge(target, "material", material);
        target.setDiscount(discount);
        target.setPrice(viewPrice);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public ChanceQuotationDto getParent() {
        return parent;
    }

    public void setParent(ChanceQuotationDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
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

    public double getViewPrice() {
        return viewPrice;
    }

    public void setViewPrice(double viewPrice) {
        this.viewPrice = viewPrice;
    }

}
