package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class AccountingEntryVbo
        extends SlimForm_htm<AccountingEntry> {

    public AccountingEntryVbo() {
        super(AccountingEntry.class);
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("codeName");
        excludes.add("label");
        excludes.add("description");
        excludes.add("beginDate");
        excludes.add("endDate");
    }

}
