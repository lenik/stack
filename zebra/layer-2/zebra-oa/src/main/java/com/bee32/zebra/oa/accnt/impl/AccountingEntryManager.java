package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 
 * @label 会计分录
 * 
 * @rel tag/: 管理标签
 * 
 * @see <a href=""></a>
 * @see <a href=""></a>
 * @see <a href=""></a>
 */
public class AccountingEntryManager
        extends FooManager {

    public AccountingEntryManager(IQueryable context) {
        super(AccountingEntry.class, context);
    }

}
