package com.bee32.zebra.oa.accnt.impl;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.sea.FooMesgVbo;

public class AccountingEventVbo
        extends FooMesgVbo<AccountingEvent> {

    public AccountingEventVbo() {
        super(AccountingEvent.class);
    }

}
