package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INIT")
public class InitAccountTicketItem
        extends AccountTicketItem {

    private static final long serialVersionUID = 1L;


}
