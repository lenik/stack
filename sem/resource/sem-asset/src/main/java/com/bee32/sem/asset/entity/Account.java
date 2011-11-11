package com.bee32.sem.asset.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 会计凭证
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_seq", allocationSize = 1)
public class Account
    extends TxEntity {

    private static final long serialVersionUID = 1L;

    List<AccountItem> items;
    BudgetRequest request;

    /**
     * 会计凭证上的条目列表
     */
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AccountItem> getItems() {
        return items;
    }

    public void setItems(List<AccountItem> items) {
        this.items = items;
    }

    @OneToMany(mappedBy = "account")
	public BudgetRequest getRequest() {
		return request;
	}

	public void setRequest(BudgetRequest request) {
		this.request = request;
	}


}
