package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * @label 会计事件
 * 
 * @rel tag/: 管理标签
 * 
 * @see <a href=""></a>
 * @see <a href=""></a>
 * @see <a href=""></a>
 */
@PathToken("acdoc")
public class AccountingEventManager
        extends FooManager {

    AccountingEventMapper mapper;

    public AccountingEventManager() {
        super(AccountingEvent.class);
        mapper = VhostDataService.getInstance().getMapper(AccountingEventMapper.class);
    }

    public AccountingEventMapper getMapper() {
        return mapper;
    }

}
