package com.bee32.sem.salary.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.world.monetary.FxrQueryException;

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
    extends MomentInterval
    implements IAccountTicketSource, DecimalConfig {

    private static final long serialVersionUID = 1L;

    AccountTicket ticket;
    BigDecimal value;

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
        return "工资";
    }

    @Transient
    @Override
    public String getTicketSrcLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(this.getBeginTime());
    }

    @Transient
    @Override
    public BigDecimal getTicketSrcValue() throws FxrQueryException {
        return this.getValue();
    }

}
