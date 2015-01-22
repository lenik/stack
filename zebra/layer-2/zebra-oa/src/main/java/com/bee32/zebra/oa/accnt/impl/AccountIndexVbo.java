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

import com.bee32.zebra.oa.accnt.Account;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class AccountIndexVbo
        extends Zc3Template_CEM<AccountIndex, Account> {

    public AccountIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountIndex.class);
        insertIndexFields("i*sa", "code", "label", "description");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Account> a, IUiRef<AccountIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        AccountMapper mapper = ctx.query(AccountMapper.class);
        List<Account> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (Account o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                tr.td().text(o.getCode());
                tr.td().text(o.getLabel());
                tr.td().text(o.getDescription()).class_("small");
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
