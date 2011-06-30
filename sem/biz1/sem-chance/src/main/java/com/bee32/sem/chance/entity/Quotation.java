package com.bee32.sem.chance.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class Quotation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    String material;
    List<QuotationDetail> details;

    public Quotation(String material, List<QuotationDetail> details) {
        super();
        this.material = material;
        this.details = details;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @OneToMany(mappedBy = "material")
    public List<QuotationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<QuotationDetail> details) {
        this.details = details;
    }

    public static Quotation pentax = new Quotation("宾得XR", Arrays.asList(//
            QuotationDetail.tempQD1,
            QuotationDetail.tempQD2,
            QuotationDetail.tempQD3,
            QuotationDetail.tempQD4));

    public static Quotation pork = new Quotation("猪肉", Arrays.asList(//
            QuotationDetail.tempQD5,
            QuotationDetail.tempQD6,
            QuotationDetail.tempQD7,
            QuotationDetail.tempQD8,
            QuotationDetail.tempQD9));
}
