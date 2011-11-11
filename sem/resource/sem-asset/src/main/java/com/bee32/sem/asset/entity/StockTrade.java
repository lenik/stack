package com.bee32.sem.asset.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@DiscriminatorValue("TRAD")
public class StockTrade extends AccountItem {

	private static final long serialVersionUID = 1L;

	List<StockTradeItem> items;

	@OneToMany(mappedBy = "trade", orphanRemoval = true)
	@Cascade(CascadeType.ALL)
	public List<StockTradeItem> getItems() {
		return items;
	}

	public void setItems(List<StockTradeItem> items) {
		this.items = items;
	}
}
