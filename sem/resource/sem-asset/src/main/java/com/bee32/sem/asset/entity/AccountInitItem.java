package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * 账务初始化明细
 *
 * 初始化时对应的科目明细，金额
 *
 * @author jack
 *
 */
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

    /**
     * 账务初始化主控类
     *
     * 账务初始化主控类
     *
     * @return
     */
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
