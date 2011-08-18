package com.bee32.sems.purchase.entity;


import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sems.bom.entity.Product;

/**
 * 生产任务明细
 */
public class ProductiveTaskItem extends EntityBean<Long> {
    private Long quantity;
    private Product product;
    private Date finishDate;    //完成时间

    private ProductiveTask productiveTask;

    @Temporal(TemporalType.TIMESTAMP)
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @ManyToOne(targetEntity = Product.class)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
}
