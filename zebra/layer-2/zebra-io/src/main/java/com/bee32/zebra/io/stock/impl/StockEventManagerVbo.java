package com.bee32.zebra.io.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.impl.CategoryDefCriteria;
import com.tinylily.model.base.schema.impl.CategoryDefMapper;
import com.tinylily.model.base.schema.impl.PhaseDefCriteria;
import com.tinylily.model.base.schema.impl.PhaseDefMapper;

public class StockEventManagerVbo
        extends Zc3Template_CEM<StockEventManager, StockEvent> {

    public StockEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(StockEventManager.class);
        insertIndexFields("i*sa", "form", "category", "subject", "text", "org", "orgUnit", "person", "quantity",
                "total", "phase");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<StockEvent> a, IUiRef<StockEventManager> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        StockEventMapper mapper = ctx.query(StockEventMapper.class);

        StockEventCriteria criteria = criteriaFromRequest(new StockEventCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("分类", true, //
                    ctx.query(CategoryDefMapper.class).filter(CategoryDefCriteria.forSchema(Schemas.STOCK)), //
                    "cat", criteria.categoryId, criteria.noCategory);
            criteria.categoryId = so.key;
            criteria.noCategory = so.isNull;

            so = filters.switchEntity("阶段", true, //
                    ctx.query(PhaseDefMapper.class).filter(PhaseDefCriteria.forSchema(Schemas.STOCK)), //
                    "phase", criteria.phaseId, criteria.noPhase);
            criteria.phaseId = so.key;
            criteria.noPhase = so.isNull;

            // HtmlDivTag valDiv = out.div().text("金额：");
            // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

            so = filters.switchPairs("年份", false, //
                    mapper.histoByYear(), "year", criteria.year, criteria.noYear);
            criteria.year = so.key;
            criteria.noYear = so.isNull;
        }

        List<StockEvent> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (StockEvent o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                ref(tr.td(), o.getForm());
                ref(tr.td(), o.getCategory());
                cocols("m", tr, o);
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getOrgUnit());
                ref(tr.td(), o.getPerson());
                tr.td().text(o.getQuantity());
                tr.td().text(o.getTotal());
                ref(tr.td(), o.getPhase());
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
