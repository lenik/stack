package com.bee32.zebra.io.sales;

import javax.persistence.Table;

import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.IdType;

/**
 * 送货单项目
 */
@IdType(Long.class)
@Table(name = "dlentry")
public class DeliveryItem
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;
    Delivery delivery;

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
     * 货物
     */
    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    /**
     * 数量
     */
    @OfGroup(OaGroups.Trade.class)
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 单价
     */
    @OfGroup(OaGroups.Trade.class)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 金额
     */
    @OfGroup(OaGroups.Trade.class)
    @Derived
    public double getTotal() {
        return price * quantity;
    }

}