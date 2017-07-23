package com.bee32.zebra.oa.accnt.impl;

import java.util.List;

import net.bodz.lily.model.util.F_YearCount;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.sql.FooMapper;

public interface AccountingEventMapper
        extends FooMapper<AccountingEvent, AccountingEventMask> {

    List<F_YearCount> histoByYear();

}
