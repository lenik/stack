package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INIT")
public class AccountInitItem
        extends AccountTicketItem {

    private static final long serialVersionUID = 1L;

    AccountInit init;

    @Override
    public void populate(Object source) {
        if (source instanceof AccountInitItem)
            _populate((AccountInitItem) source);
        else
            super.populate(source);
    }

    protected void _populate(AccountInitItem o) {
        super._populate(o);
        init = o.init;
    }

    @ManyToOne(/* optional = false: =true will break the general AccountTickItem. */)
    public AccountInit getInit() {
        return init;
    }

    public void setInit(AccountInit init) {
        if (init == null)
            throw new NullPointerException("init");
        this.init = init;
    }

}
