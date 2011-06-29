package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.QuotationItem;

public class QuotationItemDto
        extends EntityDto<QuotationItem, Long> {

    private static final long serialVersionUID = 1L;

    private QuotationInvoiceDto quotationInvoice;
    private String materialName;
    private double discount;
    private double price;
    private int number;
    private double amount;
    private String remark;

    @Override
    protected void _marshal(QuotationItem source) {
        this.quotationInvoice = new QuotationInvoiceDto().ref(source.getQuotationInvoice());
        this.materialName = source.getMaterialName();
        this.discount = source.getDiscount();
        this.price = source.getPrice();
        this.number = source.getNumber();
        this.amount = source.getAmount();
        this.remark = source.getRemark();
    }

    @Override
    protected void _unmarshalTo(QuotationItem target) {
        merge(target, "quotationInvoice", quotationInvoice);
        target.setMaterialName(materialName);
        target.setDiscount(discount);
        target.setPrice(price);
        target.setNumber(number);
        target.setAmount(amount);
        target.setRemark(remark);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public QuotationInvoiceDto getQuotationInvoice() {
        return quotationInvoice;
    }

    public void setQuotationInvoice(QuotationInvoiceDto quotationInvoice) {
        this.quotationInvoice = quotationInvoice;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
