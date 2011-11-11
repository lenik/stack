package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SALE")
public class Sale extends Trade {

	private static final long serialVersionUID = 1L;

}
