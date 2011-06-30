package com.bee32.sem.chance.entity;

import java.util.List;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class Quotation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    String material;
    List<QuotationDetail> details;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<QuotationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<QuotationDetail> details) {
        this.details = details;
    }

}
