package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.foo.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 
 * @label 会计事件
 * 
 * @rel tag/: 管理标签
 * 
 * @see <a href=""></a>
 * @see <a href=""></a>
 * @see <a href=""></a>
 */
@PathToken("accevent")
public class AccountingEventManager
        extends FooManager {

    AccountingEventMapper mapper;

    public AccountingEventManager() {
        mapper = VhostDataService.getInstance().getMapper(AccountingEventMapper.class);
    }

    public AccountingEventMapper getMapper() {
        return mapper;
    }

}
