package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class AccountingEventManagerVbo
        extends Zc3Template_CEM<AccountingEventManager, AccountingEvent> {

    public AccountingEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEventManager.class);
        formStruct = new AccountingEvent().getFormStruct();
        insertIndexFields("i*sa", "beginTime", "op", "category", "subject", "text", "topic", "org", "person",
                "debitTotal", "creditTotal");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<AccountingEventManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        AccountingEventManager event = ref.get();
        AccountingEventMapper mapper = event.getMapper();
        List<AccountingEvent> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");

        for (AccountingEvent o : list) {
            Topic topic = o.getTopic();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getBeginTime());
            ref(tr.td(), o.getOp());
            ref(tr.td(), o.getCategory());

            cocols("m", tr, o);

            tr.td().text(topic == null ? null : Strings.ellipsis(topic.getSubject(), 50)).class_("small")
                    .style("max-width: 30em");
            ref(tr.td(), o.getOrg());
            ref(tr.td(), o.getPerson());
            tr.td().text(o.getDebitTotal());
            tr.td().text(o.getCreditTotal());
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
