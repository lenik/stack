package com.bee32.sem.asset.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 资金流
 *
 * 收款单和付款单的基类。
 *
 * @author jack
 *  description: 摘要
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "fund_flow_seq", allocationSize = 1)
public class FundFlow
        extends MomentInterval
        implements
            DecimalConfig,
            IJudgeNumber,
            IAccountTicketSource {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 3000;

    Chance chance;
    Party party;
    Person operator;
    String text;
    MCValue value = new MCValue();
    AccountTicket ticket;

    BigDecimal nativeValue;

    @Override
    public void populate(Object source) {
        if (source instanceof FundFlow)
            _populate((FundFlow) source);
        else
            super.populate(source);
    }

    protected void _populate(FundFlow o) {
        super._populate(o);
        chance = o.chance;
        party = o.party;
        operator = o.operator;
        text = o.text;
        value = o.value;
        ticket = o.ticket;

        nativeValue = o.nativeValue;
    }


    /**
     * 销售机会
     *
     * @return
     */
    @ManyToOne
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 业务对象
     *
     * 本笔业务对应的公司或个人
     *
     * @return
     */
    @ManyToOne
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * 经办人
     *
     * 资金进出经办人。
     *
     * @return
     */
    @ManyToOne
    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    /**
     * 资金进出说明
     *
     * 资金进出的详细说明。
     */
    @Column(length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 金额
     *
     * 资金进出金额。
     */
    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "currencyCode", column = @Column(name = "value_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "value")) })
    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }

    public final void setValue(double value) {
        setValue(new MCValue(CurrencyConfig.getNative(), value));
    }

    /**
     * 本币金额
     *
     * 资金进出的以本地货币表达的金额
     *
     * @return
     * @throws FxrQueryException
     */
    @Redundant
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public synchronized BigDecimal getNativeValue()
            throws FxrQueryException {
        if (nativeValue == null) {
            nativeValue = value.getNativeValue(getCreatedDate());
        }
        return nativeValue;
    }

    void setNativeValue(BigDecimal nativeValue) {
        this.nativeValue = nativeValue;
    }


    /**
     * 资金进出对应的凭证
     *
     * 对应的凭证，说明本笔资金是否已经填写相应的凭证。
     *
     * @return
     */
    @Override
    @OneToOne
    @JoinColumn
    public AccountTicket getTicket() {
        return ticket;
    }

    @Override
    public void setTicket(AccountTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * 数字代表的意义
     *
     * 说明凭证上的数字的的含义
     *
     */
    @Transient
    @Override
    public String getNumberDescription() {
        return "金额";
    }

    /**
     * 审核金额
     *
     * 返回需要审核的金额数。
     *
     */
    @Transient
    @Override
    public Number getJudgeNumber() {
        try {
            return getNativeValue();
        } catch (FxrQueryException e) {
            return 0;
        }
    }

    /**
     * 凭证源Id
     */
    @Transient
    @Override
    public Serializable getTicketSrcId() {
        return this.getId();
    }

    /**
     * 凭证源类型
     */
    @Transient
    @Override
    public String getTicketSrcType() {
        String type = "";
        if (this.getClass().getName().equals("com.bee32.sem.asset.entity.CreditNote")) {
            type = "收款单";
        } else if (this.getClass().getName().equals("com.bee32.sem.asset.entity.PaymentNote")) {
            type = "付款单";
        }

        return type;
    }

    /**
     * 凭证源摘要
     */
    @Transient
    @Override
    public String getTicketSrcLabel() {
        return this.getLabel();
    }

    /**
     * 凭证源金额
     */
    @Transient
    @Override
    public BigDecimal getTicketSrcValue() throws FxrQueryException {
        return this.getNativeValue();
    }

}
