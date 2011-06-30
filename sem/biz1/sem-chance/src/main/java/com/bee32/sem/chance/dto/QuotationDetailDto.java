package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.QuotationDetail;
import com.bee32.sem.chance.util.DateToRange;

public class QuotationDetailDto
        extends EntityDto<QuotationDetail, Long> {

    private static final long serialVersionUID = 1L;

    private String date;
    private String material;
    private double discount;
    private double price;
    private String remark;

    @Override
    protected void _marshal(QuotationDetail source) {
        this.date = DateToRange.fullFormat.format(source.getCreatedDate());
        this.material = source.getMaterial();
        this.discount = source.getDiscount();
        this.price = source.getPrice();
        this.remark = source.getRemark();
    }

    @Override
    protected void _unmarshalTo(QuotationDetail target) {
        target.setMaterial(material);
        target.setDiscount(discount);
        target.setPrice(price);
        target.setRemark(remark);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
