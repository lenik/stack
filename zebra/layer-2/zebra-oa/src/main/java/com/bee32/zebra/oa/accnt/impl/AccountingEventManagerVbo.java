package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class AccountingEventManagerVbo
        extends Zc3Template_CEM<AccountingEventManager, AccountingEvent> {

    public AccountingEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEventManager.class);
        formStruct = new AccountingEvent().getFormStruct();
        setIndexFields("id", "beginTime", "label", "description", //
                "creationDate", "lastModified");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<AccountingEventManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        AccountingEventManager event = ref.get();
        AccountingEventMapper mapper = event.getMapper();
        List<AccountingEvent> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");

        for (AccountingEvent o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            tr.td().text(o.getId()).class_("col-id");
            tr.td().text(o.getBeginTime());
            tr.td().text(o.getLabel());
            tr.td().text(o.getDescription()).class_("small");
            stdcols(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
