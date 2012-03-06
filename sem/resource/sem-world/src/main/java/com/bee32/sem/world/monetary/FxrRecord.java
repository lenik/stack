package com.bee32.sem.world.monetary;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Currency;
import java.util.Date;

import javax.free.UnexpectedException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

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
@SequenceGenerator(name = "idgen", sequenceName = "fxr_record_seq", allocationSize = 1)
public class FxrRecord
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    // Currency nativeCurrency

    Date date;
    Currency unitCurrency;

    float buyingRate; // cash buying rate
    float sellingRate;

    Float baseRate;
    Float xbuyingRate; // 现汇买入价

    public FxrRecord() {
    }

    public FxrRecord(Date date, Currency unitCurrency) {
        this.date = date;
        this.unitCurrency = unitCurrency;
    }

    public FxrRecord(Date date, Currency unitCurrency, float buyingRate, float sellingRate, float baseRate) {
        this.date = date;
        this.unitCurrency = unitCurrency;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.baseRate = baseRate;
    }

X-Population

    @NaturalId
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null)
            throw new NullPointerException("date");
        this.date = date;
    }

    @Transient
    public int getDateInt() {
        return (int) (date.getTime() / 86400000);
    }

    /**
     * 单位货币 (系数为1）
     */
    @Transient
    public Currency getUnitCurrency() {
        return unitCurrency;
    }

    public void setUnitCurrency(Currency unitCurrency) {
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        this.unitCurrency = unitCurrency;
    }

    @NaturalId
    @Column(name = "unit", length = 3, nullable = false)
    String get_unit() {
        return unitCurrency.getCurrencyCode();
    }

    void set_unit(String _unitCurrency) {
        if (_unitCurrency == null)
            throw new NullPointerException("_unitCurrency");
        unitCurrency = Currency.getInstance(_unitCurrency);
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

    public Float getRate(FxrUsage usage) {
        if (usage == null)
            throw new NullPointerException("usage");
        switch (usage) {
        case BASE:
            return getBaseRate();
        case BUYING:
            return getBuyingRate();
        case SELLING:
            return getSellingRate();
        case MIDDLE:
            return getMiddleRate();
        }
        throw new UnexpectedException();
    }

    public static void main(String[] args)
            throws IntrospectionException {
        for (PropertyDescriptor pd : Introspector.getBeanInfo(FxrRecord.class).getPropertyDescriptors()) {
            System.out.println(pd.getName());
        }
    }
}
