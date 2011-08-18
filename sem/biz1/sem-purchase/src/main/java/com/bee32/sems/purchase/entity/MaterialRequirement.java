package com.bee32.sems.purchase.entity;

import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.AllowedBySupport;
import com.bee32.sems.material.entity.Material;
import com.bee32.sems.material.entity.MaterialImpl;
import com.bee32.sems.org.entity.Person;
import com.bee32.sems.srm.supplier.entity.Supplier;

/**
 * 物料需求
 */
public class MaterialRequirement extends AllowedBySupport<Long, IAllowedByContext> {
    private Long quantity;
    private Material material;
    private Date arrivalDate;
    private Supplier supplier;
    private String additionalRequirement;   //如客户指定产品需要哪种原材料
    private String memo;

    private Person creator;
    private Date createDate;

    private ProductiveTask productiveTask;
    private Order order;

    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = additionalRequirement;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @ManyToOne(targetEntity = MaterialImpl.class)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Lob
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(targetEntity = Order.class)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(targetEntity = ProductiveTask.class)
    public ProductiveTask getProductiveTask() {
        return productiveTask;
    }

    public void setProductiveTask(ProductiveTask productiveTask) {
        this.productiveTask = productiveTask;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(targetEntity = Supplier.class)
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
