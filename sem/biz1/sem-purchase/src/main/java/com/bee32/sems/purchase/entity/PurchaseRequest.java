package com.bee32.sems.purchase.entity;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.AllowedBySupport;
import com.bee32.sems.material.entity.Material;
import com.bee32.sems.org.entity.Person;

/**
 * 采购请求/采购申请/采购计划
 */
public class PurchaseRequest extends AllowedBySupport<Long, IAllowedByContext> {
    private Material material;
    private Long quantity;

    private MaterialRequirement materialRequirement;


    private Person creator;
    private Date createDate;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public MaterialRequirement getMaterialRequirement() {
        return materialRequirement;
    }

    public void setMaterialRequirement(MaterialRequirement materialRequirement) {
        this.materialRequirement = materialRequirement;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToOne(targetEntity = Person.class)
    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }
}
