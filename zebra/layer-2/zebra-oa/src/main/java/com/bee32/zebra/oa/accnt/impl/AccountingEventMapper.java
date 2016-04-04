package com.bee32.zebra.oa.accnt.impl;

import java.util.List;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.F_YearCount;

public interface AccountingEventMapper
        extends FooMapper<AccountingEvent, AccountingEventMask> {

    List<F_YearCount> histoByYear();

}
