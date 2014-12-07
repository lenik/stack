package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.foo.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

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
@PathToken("accentry")
public class AccountingEntryManager
        extends FooManager {

    AccountingEntryMapper mapper;

    public AccountingEntryManager() {
        mapper = VhostDataService.getInstance().getMapper(AccountingEntryMapper.class);
    }

    public AccountingEntryMapper getMapper() {
        return mapper;
    }

}
