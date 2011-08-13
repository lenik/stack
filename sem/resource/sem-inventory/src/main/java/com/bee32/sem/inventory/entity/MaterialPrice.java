package com.bee32.sem.inventory.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.misc.i18n.CurrencyConfig;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 物料的基准价格（随时间变化）。
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "material_price_seq", allocationSize = 1)
public class MaterialPrice
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Material material;
    Date date = new Date();
// Date date = LocalDateUtil.truncate(new Date());
    MCValue price = new MCValue();

    /**
     * 物料
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 价格有效起始时间。
     */
    @NaturalId
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null)
            throw new NullPointerException("date");
// date = LocalDateUtil.truncate(date);
        this.date = date;
    }

    /**
     * 价格
     */
    @Embedded
    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

    @Transient
    public BigDecimal getNativePrice()
            throws FxrQueryException {
        MCValue mcv = price.toNative(date);
        return mcv.getValue();
    }

    /**
     * Set price in native currency.
     */
    public final void setPrice(double price) {
        setPrice(new MCValue(CurrencyConfig.getNative(), price));
    }

}
