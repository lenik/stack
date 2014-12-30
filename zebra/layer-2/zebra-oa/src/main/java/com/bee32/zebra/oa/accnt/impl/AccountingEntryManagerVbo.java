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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class AccountingEntryManagerVbo
        extends Zc3Template_CEM<AccountingEntryManager, AccountingEntry> {

    public AccountingEntryManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEntryManager.class);
        formDef = new AccountingEntry().getFormDef();
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<AccountingEntryManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        AccountingEntryMapper mapper = ctx.query(AccountingEntryMapper.class);
        List<AccountingEntry> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");

        for (AccountingEntry o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getDescription()).class_("small");
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
