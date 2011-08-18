package com.bee32.sems.purchase.entity;

import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sems.bom.entity.Product;

/**
 * 定单明细项目
 */
public class OrderItem extends EntityBean<Long> {

    private Order order;
    private Long quantity;
    private Product product;

    @ManyToOne(targetEntity = Order.class)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(targetEntity = Product.class)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
