package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.tk.sea.FooManager;
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
@PathToken("acentry")
public class AccountingEntryManager
        extends FooManager {

    AccountingEntryMapper mapper;

    public AccountingEntryManager() {
        super(AccountingEntry.class);
        mapper = VhostDataService.getInstance().getMapper(AccountingEntryMapper.class);
    }

    public AccountingEntryMapper getMapper() {
        return mapper;
    }

}
