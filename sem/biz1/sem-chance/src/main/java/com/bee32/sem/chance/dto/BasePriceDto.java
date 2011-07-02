package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.BasePrice;
import com.bee32.sem.chance.util.DateToRange;

public class BasePriceDto
        extends EntityDto<BasePrice, Long> {

    private static final long serialVersionUID = 1L;

    private String date;
    private String material;
    private double discount;
    private double price;
    private String remark;

    @Override
    protected void _marshal(BasePrice source) {
        this.date = DateToRange.fullFormat.format(source.getCreatedDate());
        this.material = source.getMaterial();
        this.price = source.getPrice();
        this.remark = source.getRemark();
    }

    @Override
    protected void _unmarshalTo(BasePrice target) {
        target.setMaterial(material);
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
