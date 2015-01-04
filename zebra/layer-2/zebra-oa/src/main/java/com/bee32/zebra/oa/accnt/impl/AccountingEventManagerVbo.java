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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.impl.FormDefCriteria;
import com.tinylily.model.base.schema.impl.FormDefMapper;

public class AccountingEventManagerVbo
        extends Zc3Template_CEM<AccountingEventManager, AccountingEvent> {

    public AccountingEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AccountingEventManager.class);
        insertIndexFields("i*sa", "beginDate", "op", "category", "subject", "text", "topic", "org", "person",
                "debitTotal", "creditTotal");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<AccountingEventManager> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        AccountingEventMapper mapper = ctx.query(AccountingEventMapper.class);

        AccountingEventCriteria criteria = criteriaFromRequest(new AccountingEventCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(page.mainCol, "s-filter");
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

        List<AccountingEvent> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");

        for (AccountingEvent o : list) {
            Topic topic = o.getTopic();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(fn.formatDate(o.getBeginDate()));
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

        dumpFullData(page.extradata, list);
    }
}
