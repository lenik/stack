package com.bee32.zebra.io.sales;

import javax.persistence.Table;

import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.model.base.CoMomentInterval;
import net.bodz.lily.model.base.IdType;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.oa.OaGroups;

/**
 * 送货单项目
 */
@IdType(Long.class)
@Table(name = "dlentry")
public class DeliveryItem
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;

    public static final int N_ALT_LABEL = 30;
    public static final int N_ALT_SPEC = 80;

    Delivery delivery;
    SalesOrder salesOrder;
    SalesOrderItem salesOrderItem;
    Artifact artifact;

    double quantity;
    double price;

    /**
     * 送货单
     */
    @OfGroup(StdGroup.Process.class)
    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    /**
     * 销售订单
     */
    @OfGroup(StdGroup.Process.class)
    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    /**
     * 源订单项
     */
    @OfGroup(StdGroup.Process.class)
    public SalesOrderItem getSalesOrderItem() {
        return salesOrderItem;
    }

    public void setSalesOrderItem(SalesOrderItem salesOrderItem) {
        this.salesOrderItem = salesOrderItem;
    }

    /**
     * 货物
     */
    @Derived
    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    /**
     * 出货数量
     */
    @OfGroup(OaGroups.Trade.class)
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 出货价
     */
    @OfGroup(OaGroups.Trade.class)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 出货金额
     */
    @OfGroup(OaGroups.Trade.class)
    @Derived
    public double getTotal() {
        return price * quantity;
    }

}