package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

/**
 * 外汇的相关术语。
 * <ul>
 * <li>卖出价是银行将外币卖给客户的牌价，也就是客户到银行购汇时的牌价；
 * <li>而买入价则是银行向客户买入外汇或外币时的牌价，它分为现钞买入价和现汇买入价两种。
 * <li>现汇买入价是银行买入现汇时的牌价， 而现钞买入价则是银行买入外币现钞时的牌价。
 * <li>现汇和现钞是不同的概念，是外币存入银行后的两种不同形态。钞是可以存入取出的，汇不行，只能兑换成钞才可以取； 但汇是可以像汇款一样汇往国外的，钞不行，一定要兑换成现汇。
 * 至于为什么会不同，那是因为银行在外汇交易的过程中会承担风险，所以要控制价差赚取提供服务的费用。 现汇卖出价和现钞卖出价是相同的，即卖出价。
 * <li>中间价= 现汇买入价+现汇卖出价）/2，中间价是市场所形成的。
 * <li>基准价是人民银行公布的一种中间价，其他商业银行可在基准价基础上，按照人行规定的浮动范围制定自己的买入、卖出价。 基准价是人民银行公布的。
 * </ul>
 */
@Entity
@Blue
public class FxrRecord
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date quoteDate;
    Currency quoteCurrency;

    float buyingRate; // cash buying rate
    float sellingRate;

    Float baseRate;
    Float xbuyingRate; // 现汇买入价

    public FxrRecord() {
    }

    public FxrRecord(Date quoteDate, Currency quoteCurrency) {
        this.quoteDate = quoteDate;
        this.quoteCurrency = quoteCurrency;
    }

    @NaturalId
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        if (quoteDate == null)
            throw new NullPointerException("quoteDate");
        this.quoteDate = quoteDate;
    }

    // Currency unitCurrency;
    //
    // /**
    // * 单元货币、或称 base currency, transation currency.
    // */
    // @Transient
    // public Currency getUnitCurrency() {
    // return unitCurrency;
    // }
    //
    // public void setUnitCurrency(Currency unitCurrency) {
    // if (unitCurrency == null)
    // throw new NullPointerException("unitCurrency");
    // this.unitCurrency = unitCurrency;
    // }
    //
    // @NaturalId
    // @Column(length = 3, nullable = false)
    // String get_Unit() {
    // return unitCurrency.getCurrencyCode();
    // }
    //
    // void set_Unit(String _unitCurrency) {
    // if (_unitCurrency == null)
    // throw new NullPointerException("_unitCurrency");
    // unitCurrency = Currency.getInstance(_unitCurrency);
    // }

    /**
     * 报价货币（分子）、或称 price currency, payment currency
     */
    @Transient
    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(Currency quoteCurrency) {
        if (quoteCurrency == null)
            throw new NullPointerException("quoteCurrency");
        this.quoteCurrency = quoteCurrency;
    }

    @NaturalId
    @Column(length = 3, nullable = false)
    String get_Quote() {
        return quoteCurrency.getCurrencyCode();
    }

    void set_Quote(String _quoteCurrency) {
        if (_quoteCurrency == null)
            throw new NullPointerException("_quoteCurrency");
        quoteCurrency = Currency.getInstance(_quoteCurrency);
    }

    /**
     * （现钞）买入价
     */
    @Column(nullable = false)
    public float getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(float buyingRate) {
        this.buyingRate = buyingRate;
    }

    /**
     * 卖出价
     */
    @Column(nullable = false)
    public float getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(float sellingRate) {
        this.sellingRate = sellingRate;
    }

    @Transient
    public float getMiddleRate() {
        float middleRate = (buyingRate + sellingRate) / 2;
        return middleRate;
    }

    /**
     * 基准价（可选）
     */
    public Float getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Float baseRate) {
        this.baseRate = baseRate;
    }

    /**
     * 现汇买入价（可选）
     */
    public Float getXbuyingRate() {
        return xbuyingRate;
    }

    public void setXbuyingRate(Float xbuyingRate) {
        this.xbuyingRate = xbuyingRate;
    }

}
