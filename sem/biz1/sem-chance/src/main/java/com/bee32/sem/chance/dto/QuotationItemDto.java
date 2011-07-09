package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.QuotationItem;

public class QuotationItemDto
        extends EntityDto<QuotationItem, Long> {

    private static final long serialVersionUID = 1L;

    private QuotationDto quotation;
    private String material;
    private double discount;
    private BasePriceDto basePrice;
    private Double price;
    private Integer number;
    private double amount;
    private String remark;

    @Override
    protected void _marshal(QuotationItem source) {
        this.quotation = new QuotationDto().ref(source.getQuotation());
        this.material = source.getMaterial();
        this.discount = source.getDiscount();
        this.basePrice = mref(BasePriceDto.class, source.getBasePrice());
        this.price = source.getPrice();
        this.number = source.getNumber();
        this.amount = source.getNumber() * source.getPrice();
        this.remark = source.getRemark();
    }

    @Override
    protected void _unmarshalTo(QuotationItem target) {
        merge(target, "quotation", quotation);
        merge(target, "basePrice", basePrice);
        target.setMaterial(material);
        target.setDiscount(discount);
        target.setPrice(price);
        target.setNumber(number);
        target.setRemark(remark);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public QuotationDto getQuotation() {
        return quotation;
    }

    public void setQuotationInvoice(QuotationDto quotation) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
