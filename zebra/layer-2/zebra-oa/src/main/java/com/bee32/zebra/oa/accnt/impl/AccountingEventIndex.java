package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * @label 凭证单
 * 
 * @rel acdoc/?phase=2: 管理会计分录
 * @rel cat/?schema=8: 管理标签
 * 
 * @see <a href=""></a>
 * @see <a href=""></a>
 * @see <a href=""></a>
 */
@ObjectType(AccountingEvent.class)
public class AccountingEventIndex
        extends QuickIndex {

    public AccountingEventIndex(IQueryable context) {
        super(context);
    }

}
