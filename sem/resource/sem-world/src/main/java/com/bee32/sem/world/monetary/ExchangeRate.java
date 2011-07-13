package com.bee32.sem.world.monetary;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;

@Entity
public class ExchangeRate
        extends EntityBean<Long>
        implements IEntity<Long> {

    private static final long serialVersionUID = 1L;

    CurrencyCode from;
    CurrencyCode to;
    float rate;
    Date date;

    public ExchangeRate() {
    }

    public ExchangeRate(CurrencyCode from, CurrencyCode to, float rate, Date date) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.date = date;
    }

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public CurrencyCode getFrom() {
        return from;
    }

    public void setFrom(CurrencyCode from) {
        if (from == null)
            throw new NullPointerException("from");
        this.from = from;
    }

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public CurrencyCode getTo() {
        if (to == null)
            throw new NullPointerException("to");
        return to;
    }

    public void setTo(CurrencyCode to) {
        this.to = to;
    }

    @Column(precision = 10, scale = 3)
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
