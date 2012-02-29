package com.bee32.sem.chance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.sem.world.thing.AbstractItem;

/**
 * 报价
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "hint_product_quotation_seq", allocationSize = 1)
public class WantedProductQuotation
        extends AbstractItem {

    private static final long serialVersionUID = 1L;

    WantedProduct product;
    BigDecimal discountRate = new BigDecimal(1);

    @ManyToOne
    public WantedProduct getProduct() {
        return product;
    }

    public void setProduct(WantedProduct product) {
        if (product == null)
            throw new NullPointerException("product");
        this.product = product;
    }

    /**
     * 折扣率，按中国人的折扣率习惯填写
     *
     * @return
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    @Transient
    protected Date getFxrDate() {
        return this.getCreatedDate();
    }

}
