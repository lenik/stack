package com.bee32.sem.asset.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@DiscriminatorValue("TRAD")
public class Trade extends AccountDocItem {

	private static final long serialVersionUID = 1L;

	List<TradeItem> items;

	@OneToMany(mappedBy = "trade", orphanRemoval = true)
	@Cascade(CascadeType.ALL)
	public List<TradeItem> getItems() {
		return items;
	}

	public void setItems(List<TradeItem> items) {
		this.items = items;
	}
}
