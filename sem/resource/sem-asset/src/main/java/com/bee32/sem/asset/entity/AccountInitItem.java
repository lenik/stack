package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INIT")
public class AccountInitItem
        extends AccountTicketItem {

    private static final long serialVersionUID = 1L;

    AccountInit parent;

    @ManyToOne(optional = false)
    public AccountInit getParent() {
        return parent;
    }

    public void setParent(AccountInit parent) {
        this.parent = parent;
    }

}
