package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PURC")
public class StockPurchase extends StockTrade {

	private static final long serialVersionUID = 1L;

}
