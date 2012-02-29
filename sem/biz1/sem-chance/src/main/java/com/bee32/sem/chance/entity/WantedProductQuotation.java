package com.bee32.sem.chance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
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
@SequenceGenerator(name = "idgen", sequenceName = "wanted_product_quotation_seq", allocationSize = 1)
public class WantedProductQuotation
        extends AbstractItem {

    private static final long serialVersionUID = 1L;

    public static final int DISCOUNT_SCALE = 1000;
    static final BigDecimal DISCOUNT_SCALE_REAL = new BigDecimal(DISCOUNT_SCALE);

    WantedProduct product;
    int discount = DISCOUNT_SCALE;

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
    @Column(nullable = false)
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Transient
    public BigDecimal getDiscountReal() {
        BigDecimal discountReal = new BigDecimal(discount).divide(DISCOUNT_SCALE_REAL);
        return discountReal;
    }

    @Override
    @Transient
    protected Date getFxrDate() {
        return this.getCreatedDate();
    }

}
