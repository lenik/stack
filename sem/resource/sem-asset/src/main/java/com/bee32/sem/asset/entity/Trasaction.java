package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.world.monetary.MCValue;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "transaction_seq", allocationSize = 1)
public class Trasaction extends TxEntity {

	private static final long serialVersionUID = 1L;

    public static final int SUMMARY_LENGTH = 50;

	String summary;
	String text;

	MCValue  money = new MCValue();

	AccountDoc accountDoc;

	@Column(length = SUMMARY_LENGTH)
    public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Lob
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currencyCode", column = @Column(name = "money_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "money")) })
	public MCValue getMoney() {
		return money;
	}

	public void setMoney(MCValue money) {
        if (money == null)
            throw new NullPointerException("money");
        this.money = money;
	}

    public final void setMoney(double money) {
        setMoney(new MCValue(CurrencyConfig.getNative(), money));
    }

    @ManyToOne
	public AccountDoc getAccountDoc() {
		return accountDoc;
	}

	public void setAccountDoc(AccountDoc accountDoc) {
		this.accountDoc = accountDoc;
	}


}
