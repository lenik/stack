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
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.CategoryDef;

public class AccountingEventManagerVbo
        extends Zc3Template_CEM<AccountingEventManager, AccountingEvent> {

    public AccountingEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEventManager.class);
        formStruct = new AccountingEvent().getFormStruct();
        insertIndexFields("beginTime",//
                "op", "category", "subject", "text",//
                "topic", "org", "person", "debitTotal", "creditTotal");
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
            CategoryDef category = o.getCategory();
            Topic topic = o.getTopic();
            Organization org = o.getOrg();
            Person person = o.getPerson();

            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getBeginTime());
            tr.td().text(o.getOp() == null ? null : o.getOp().getLabel());
            tr.td().text(category == null ? null : category.getLabel());
            tr.td().b().text(o.getSubject()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getText(), 50)).class_("small").style("max-width: 30em");

            tr.td().text(topic == null ? null : Strings.ellipsis(topic.getSubject(), 50)).class_("small")
                    .style("max-width: 30em");
            tr.td().text(org == null ? null : org.getLabel());
            tr.td().text(person == null ? null : person.getLabel());
            tr.td().text(o.getDebitTotal());
            tr.td().text(o.getCreditTotal());
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
