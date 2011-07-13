package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

@Entity
@Blue
public class FxrRecord
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Currency from;
    Currency to;
    float rate;
    Date date;

    public FxrRecord() {
    }

    public FxrRecord(Currency from, Currency to, float rate, Date date) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.date = date;
    }

    @Transient
    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        if (from == null)
            throw new NullPointerException("from");
        this.from = from;
    }

    @Column(length = 3, nullable = false)
    String get_From() {
        return from.getCurrencyCode();
    }

    void set_From(String _from) {
        if (_from == null)
            throw new NullPointerException("_from");
        from = Currency.getInstance(_from);
    }

    @Transient
    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        if (to == null)
            throw new NullPointerException("to");
        this.to = to;
    }

    @Column(length = 3, nullable = false)
    String get_To() {
        return to.getCurrencyCode();
    }

    void set_To(String _to) {
        if (_to == null)
            throw new NullPointerException("_to");
        to = Currency.getInstance(_to);
    }

    @Column(nullable = false)
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
