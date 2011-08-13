package com.bee32.sems.bom.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sems.material.entity.MaterialImpl;

@Entity
public class Component extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 对应物料
     */
    private MaterialImpl material;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 描述
     */
    private String desc;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 起用日期
     */
    private Date validDateFrom;

    /**
     * 无效日期
     */
    private Date validDateTo;

    /**
     * 本条bom明细对应的product(product与part一对多，组成具体bom)
     */
    private Product product;


    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Date validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Date validDateTo) {
        this.validDateTo = validDateTo;
    }

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public MaterialImpl getMaterial() {
        return material;
    }

    public void setMaterial(MaterialImpl material) {
        this.material = material;
    }

}
