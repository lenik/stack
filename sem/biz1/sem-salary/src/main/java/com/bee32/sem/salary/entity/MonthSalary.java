package com.bee32.sem.salary.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;

/**
 * 月度工资冗余
 *
 * 所有员工某月的工资合计数. beginTime代表月份
 *
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "month_salary_seq", allocationSize = 1)
public class MonthSalary
        extends EntityAuto<Long>
        implements IAccountTicketSource, DecimalConfig {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    AccountTicket ticket;
    BigDecimal value;

    /**
     * 年月
     *
     * 年月的组合，如 201208。
     */
    @Column(nullable = false)
    public int getYearMonth() {
        return year * 100 + month;
    }

    public void setYearMonth(int yearMonth) {
        year = yearMonth / 100;
        month = yearMonth % 100;
    }

    /**
     * 工资条对应年
     */
    @Transient
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 工资条对应月份
     */
    @Transient
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    @ManyToOne
    public AccountTicket getTicket() {
        return ticket;
    }

    @Override
    public void setTicket(AccountTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * 月工资总额
     *
     * @return
     */
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Transient
    @Override
    public Serializable getTicketSrcId() {
        return this.getId();
    }

    @Transient
    @Override
    public String getTicketSrcType() {
        return "月工资发放总额";
    }

    @Transient
    @Override
    public String getTicketSrcLabel() {
        return year + "-" + month;
    }

    @Transient
    @Override
    public BigDecimal getTicketSrcValue() {
        return this.getValue();
    }

}
