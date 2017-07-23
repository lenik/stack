package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.schema.impl.FormDefMapper;
import net.bodz.lily.model.base.schema.impl.FormDefMask;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class AccountingEventIndex_htm
        extends SlimIndex_htm<AccountingEventIndex, AccountingEvent, AccountingEventMask> {

    public AccountingEventIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEventIndex.class);
        indexFields.parse("i*sa", "beginDate", "op", "category", "subject", "text", "topic", "org", "person",
                "debitTotal", "creditTotal");
    }

    @Override
    protected AccountingEventMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        AccountingEventMapper mapper = ctx.query(AccountingEventMapper.class);

        AccountingEventMask mask = VarMapState.restoreFrom(new AccountingEventMask(), ctx.getRequest());
        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("表单", true, //
                ctx.query(FormDefMapper.class).filter(FormDefMask.forSchema(Schemas.ACCOUNTING)), //
                "form", mask.formId, mask.noForm);
        mask.formId = sw.getSelection1();
        mask.noForm = sw.isSelectNull();

        // HtmlDiv valDiv = out.div().text("金额：");
        // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

        sw = switchers.entryOf("年份", false, //
                mapper.histoByYear(), "year", mask.year, mask.noYear);
        mask.year = sw.getSelection1();
        mask.noYear = sw.isSelectNull();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<AccountingEventIndex> ref)
            throws ViewBuilderException, IOException {
        AccountingEventMapper mapper = ctx.query(AccountingEventMapper.class);
        AccountingEventMask mask = ctx.query(AccountingEventMask.class);
        List<AccountingEvent> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (AccountingEvent o : list) {
            Topic topic = o.getTopic();

            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(fmt.formatDate(o.getBeginDate()));
            ref(tr.td(), o.getOp());
            //ref(tr.td(), o.getCategory());

            itab.cocols("m", tr, o);

            tr.td().text(topic == null ? null : Strings.ellipsis(topic.getSubject(), 50)).class_("small")
                    .style("max-width: 30em");
            ref(tr.td(), o.getOrg());
            ref(tr.td(), o.getPerson());
            tr.td().text(o.getDebitTotal());
            tr.td().text(o.getCreditTotal());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
