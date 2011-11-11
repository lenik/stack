package com.bee32.sem.asset.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.transaction.Transaction;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 会计凭证
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_doc_seq", allocationSize = 1)
public class AccountDoc
    extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int SUMMARY_LENGTH = 50;

    List<AccountDocItem> items;
    String summary;

    Transaction transaction;

    /**
     * 会计凭证上的条目列表
     */
    @OneToMany(mappedBy = "accountDoc", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AccountDocItem> getItems() {
        return items;
    }

    public void setItems(List<AccountDocItem> items) {
        this.items = items;
    }

    /**
     * 撞要
     */
    @Column(length = SUMMARY_LENGTH)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @OneToMany(mappedBy = "accountDoc")
	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


}
