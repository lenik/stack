package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.tinylily.model.base.schema.impl.FormDefCriteria;
import com.tinylily.model.base.schema.impl.FormDefMapper;

public class AccountingEventIndexVbo
        extends SlimIndex_htm<AccountingEventIndex, AccountingEvent> {

    public AccountingEventIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEventIndex.class);
        indexFields.parse("i*sa", "beginDate", "op", "category", "subject", "text", "topic", "org", "person",
                "debitTotal", "creditTotal");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<AccountingEvent> a,
            IUiRef<AccountingEventIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        AccountingEventMapper mapper = ctx.query(AccountingEventMapper.class);

        AccountingEventCriteria criteria = fn.criteriaFromRequest(new AccountingEventCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("表单", true, //
                    ctx.query(FormDefMapper.class).filter(FormDefCriteria.forSchema(Schemas.ACCOUNTING)), //
                    "form", criteria.formId, criteria.noForm);
            criteria.formId = so.key;
            criteria.noForm = so.isNull;

            // HtmlDivTag valDiv = out.div().text("金额：");
            // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

            so = filters.switchPairs("年份", false, //
                    mapper.histoByYear(), "year", criteria.year, criteria.noYear);
            criteria.year = so.key;
            criteria.noYear = so.isNull;
        }

        List<AccountingEvent> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (AccountingEvent o : list) {
                Topic topic = o.getTopic();

                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(fmt.formatDate(o.getBeginDate()));
                ref(tr.td(), o.getOp());
                ref(tr.td(), o.getCategory());

                itab.cocols("m", tr, o);

                tr.td().text(topic == null ? null : Strings.ellipsis(topic.getSubject(), 50)).class_("small")
                        .style("max-width: 30em");
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getPerson());
                tr.td().text(o.getDebitTotal());
                tr.td().text(o.getCreditTotal());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
