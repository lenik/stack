package com.bee32.zebra.oa.accnt.impl;

import com.bee32.zebra.oa.accnt.Account;
import com.bee32.zebra.tk.sea.FooVbo;

public class AccountVbo
        extends FooVbo<Account> {

    public AccountVbo() {
        super(Account.class);
    }

}
