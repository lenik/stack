package com.bee32.zebra.io.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.impl.CategoryDefCriteria;
import net.bodz.lily.model.base.schema.impl.CategoryDefMapper;
import net.bodz.lily.model.base.schema.impl.PhaseDefCriteria;
import net.bodz.lily.model.base.schema.impl.PhaseDefMapper;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class StockEventIndex_htm
        extends SlimIndex_htm<StockEventIndex, StockEvent, StockEventCriteria> {

    public StockEventIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(StockEventIndex.class);
        indexFields.parse("i*sa", "form", "category", "subject", "text", "org", "orgUnit", "person", "quantity",
                "total", "phase");
    }

    @Override
    protected StockEventCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        StockEventMapper mapper = ctx.query(StockEventMapper.class);
        StockEventCriteria criteria = CriteriaBuilder.fromRequest(new StockEventCriteria(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("分类", true, //
                ctx.query(CategoryDefMapper.class).filter(CategoryDefCriteria.forSchema(Schemas.STOCK)), //
                "cat", criteria.categoryId, criteria.noCategory);
        criteria.categoryId = sw.getSelection1();
        criteria.noCategory = sw.isSelectNull();

        sw = switchers.entityOf("阶段", true, //
                ctx.query(PhaseDefMapper.class).filter(PhaseDefCriteria.forSchema(Schemas.STOCK)), //
                "phase", criteria.phaseId, criteria.noPhase);
        criteria.phaseId = sw.getSelection1();
        criteria.noPhase = sw.isSelectNull();

        // HtmlDivTag valDiv = out.div().text("金额：");
        // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

        sw = switchers.entryOf("年份", false, //
                mapper.histoByYear(), "year", criteria.year, criteria.noYear);
        criteria.year = sw.getSelection1();
        criteria.noYear = sw.isSelectNull();

        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<StockEvent> a, IUiRef<StockEventIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        StockEventMapper mapper = ctx.query(StockEventMapper.class);
        StockEventCriteria criteria = ctx.query(StockEventCriteria.class);
        List<StockEvent> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (StockEvent o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getForm());
                ref(tr.td(), o.getCategory());
                itab.cocols("m", tr, o);
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getOrgUnit());
                ref(tr.td(), o.getPerson());
                tr.td().text(o.getQuantity());
                tr.td().text(o.getTotal());
                ref(tr.td(), o.getPhase());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
