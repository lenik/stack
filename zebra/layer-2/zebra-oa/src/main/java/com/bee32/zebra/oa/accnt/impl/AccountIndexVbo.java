package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.Account;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class AccountIndexVbo
        extends SlimIndex_htm<AccountIndex, Account, AccountCriteria> {

    public AccountIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected AccountCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        AccountCriteria criteria = CriteriaBuilder.fromRequest(new AccountCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<Account> a, IUiRef<AccountIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        AccountMapper mapper = ctx.query(AccountMapper.class);
        AccountCriteria criteria = ctx.query(AccountCriteria.class);
        List<Account> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Account o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getCode());
                tr.td().text(o.getLabel());
                tr.td().text(o.getDescription()).class_("small");
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
