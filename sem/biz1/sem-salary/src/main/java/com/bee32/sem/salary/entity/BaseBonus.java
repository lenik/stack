package com.bee32.sem.salary.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.free.Dates;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * （公共）奖金/补贴设置
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "base_bonus_seq", allocationSize = 1)
public class BaseBonus
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    static final Date defaultDate;
    static {
        try {
            defaultDate = Dates.YYYY_MM_DD.parse("2000-1-1");
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    BigDecimal bonus;

    public BaseBonus() {
    }

    public BaseBonus(String label, BigDecimal bonus) {
        this(label, defaultDate, bonus);
    }

    public BaseBonus(String label, Date date, BigDecimal bonus) {
        setLabel(label);
        setBeginTime(date);
        setBonus(bonus);
    }

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
