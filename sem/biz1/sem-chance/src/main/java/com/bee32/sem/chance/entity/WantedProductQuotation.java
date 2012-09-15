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
 * 选型产品的报价。
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

    @Override
    public void populate(Object source) {
        if (source instanceof WantedProductQuotation)
            _populate((WantedProductQuotation) source);
        else
            super.populate(source);
    }

    protected void _populate(WantedProductQuotation o) {
        super._populate(o);
        product = o.product;
        discount = o.discount;
    }

    /**
     * 选型产品
     *
     * 报价对应的选型产品。
     *
     * @return
     */
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
     * 折扣率
     *
     * 按中国人的折扣率习惯填写。
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

    /**
     * 实际折扣率
     *
     * 以小数表示的折扣率。
     *
     * @return
     */
    @Transient
    public BigDecimal getDiscountReal() {
        BigDecimal discountReal = new BigDecimal(discount).divide(DISCOUNT_SCALE_REAL);
        return discountReal;
    }

    /**
     * 汇率日期
     *
     * 汇率每天都变动，所以有一个日期。
     */
    @Override
    @Transient
    protected Date getFxrDate() {
        return this.getCreatedDate();
    }

}
