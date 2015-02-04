package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class AccountingEntryIndexVbo
        extends SlimIndex_htm<AccountingEntryIndex, AccountingEntry, AccountingEntryCriteria> {

    public static final String[] FIELDS = { "event", "account", "debitSide", "value" };

    public AccountingEntryIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEntryIndex.class);
        indexFields.parse("i*", FIELDS);
    }

    @Override
    protected AccountingEntryCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        AccountingEntryCriteria criteria = fn.criteriaFromRequest(new AccountingEntryCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<AccountingEntry> a,
            IUiRef<AccountingEntryIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        AccountingEntryMapper mapper = ctx.query(AccountingEntryMapper.class);
        AccountingEntryCriteria criteria = ctx.query(AccountingEntryCriteria.class);
        List<AccountingEntry> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (AccountingEntry o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getEvent());
                ref(tr.td(), o.getAccount());
                tr.td().text(o.isDebitSide() ? "借" : "贷");
                tr.td().text(o.getValue().abs());
            }
    }

}
