package com.bee32.sems.purchase.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.AllowedBySupport;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.org.entity.Person;

/**
 * 生产任务
 */
public class ProductiveTask extends AllowedBySupport<Long, IAllowedByContext> {
    private Person creator;
    private Date createDate;
    private Order order;

    private List<ProductiveTaskItem> items;

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

    @OneToMany(targetEntity = ProductiveTaskItem.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productiveTask")
    public List<ProductiveTaskItem> getItems() {
        return items;
    }

    public void setItems(List<ProductiveTaskItem> items) {
        this.items = items;
    }

    @ManyToOne(targetEntity = Order.class)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
