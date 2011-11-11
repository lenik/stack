package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.world.monetary.MCValue;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "budget_request_seq", allocationSize = 1)
public class BudgetRequest extends TxEntity {

	private static final long serialVersionUID = 1L;

	public static final int TEXT_LENGTH = 10000;

	String text;
	MCValue value = new MCValue();
	Account account;

	/**
	 * 业务详细说明
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
	 */
	@Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currencyCode", column = @Column(name = "value_cc")), //
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

    @ManyToOne
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
