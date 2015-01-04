package com.bee32.zebra.oa.accnt.impl;

import java.util.List;

import net.bodz.bas.db.batis.IMapperTemplate;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.util.F_YearCount;

public interface AccountingEventMapper
        extends IMapperTemplate<AccountingEvent, AccountingEventCriteria> {

    List<F_YearCount> histoByYear();

}
