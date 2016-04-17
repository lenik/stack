package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class AccountingEntryIndex_htm
        extends SlimIndex_htm<AccountingEntryIndex, AccountingEntry, AccountingEntryMask> {

    public static final String[] FIELDS = { "event", "account", "side", "absValue" };

    public AccountingEntryIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEntryIndex.class);
        indexFields.parse("i*", FIELDS);
    }

    @Override
    protected AccountingEntryMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        AccountingEntryMask mask = MaskBuilder.fromRequest(new AccountingEntryMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<AccountingEntryIndex> ref)
            throws ViewBuilderException, IOException {
        AccountingEntryMapper mapper = ctx.query(AccountingEntryMapper.class);
        AccountingEntryMask mask = ctx.query(AccountingEntryMask.class);
        List<AccountingEntry> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (AccountingEntry o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            ref(tr.td(), o.getEvent());
            ref(tr.td(), o.getAccount());
            tr.td().text(o.getSide().getLabel());
            tr.td().text(o.getValue().abs());
        }
        return list;
    }

}
