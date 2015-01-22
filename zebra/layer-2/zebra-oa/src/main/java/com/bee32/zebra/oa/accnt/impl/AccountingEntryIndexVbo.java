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
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class AccountingEntryIndexVbo
        extends Zc3Template_CEM<AccountingEntryIndex, AccountingEntry> {

    public AccountingEntryIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEntryIndex.class);
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<AccountingEntry> a,
            IUiRef<AccountingEntryIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        AccountingEntryMapper mapper = ctx.query(AccountingEntryMapper.class);
        List<AccountingEntry> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (AccountingEntry o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                tr.td().text(o.getDescription()).class_("small");
                cocols("sa", tr, o);
            }
    }

}
