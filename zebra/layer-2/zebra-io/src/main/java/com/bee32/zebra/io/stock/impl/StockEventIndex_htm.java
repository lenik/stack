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
import net.bodz.lily.model.base.schema.impl.CategoryDefMask;
import net.bodz.lily.model.base.schema.impl.CategoryDefMapper;
import net.bodz.lily.model.base.schema.impl.PhaseDefMask;
import net.bodz.lily.model.base.schema.impl.PhaseDefMapper;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class StockEventIndex_htm
        extends SlimIndex_htm<StockEventIndex, StockEvent, StockEventMask> {

    public StockEventIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(StockEventIndex.class);
        indexFields.parse("i*sa", "form", "category", "subject", "text", "org", "orgUnit", "person", "entryCount",
                "quantity", "total", "phase");
    }

    @Override
    protected StockEventMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        StockEventMapper mapper = ctx.query(StockEventMapper.class);
        StockEventMask mask = MaskBuilder.fromRequest(new StockEventMask(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("分类", true, //
                ctx.query(CategoryDefMapper.class).filter(CategoryDefMask.forSchema(Schemas.STOCK)), //
                "cat", mask.categoryId, mask.noCategory);
        mask.categoryId = sw.getSelection1();
        mask.noCategory = sw.isSelectNull();

        sw = switchers.entityOf("阶段", true, //
                ctx.query(PhaseDefMapper.class).filter(PhaseDefMask.forSchema(Schemas.STOCK)), //
                "phase", mask.phaseId, mask.noPhase);
        mask.phaseId = sw.getSelection1();
        mask.noPhase = sw.isSelectNull();

        // HtmlDivTag valDiv = out.div().text("金额：");
        // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

        sw = switchers.entryOf("年份", false, //
                mapper.histoByYear(), "year", mask.year, mask.noYear);
        mask.year = sw.getSelection1();
        mask.noYear = sw.isSelectNull();

        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<StockEvent> a, IUiRef<StockEventIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        StockEventMapper mapper = ctx.query(StockEventMapper.class);
        StockEventMask mask = ctx.query(StockEventMask.class);
        List<StockEvent> list = a.noList() ? null : postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (StockEvent o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getForm().getDef());
                ref(tr.td(), o.getCategory());
                itab.cocols("m", tr, o);
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getOrgUnit());
                ref(tr.td(), o.getPerson());
                tr.td().text(o.getEntryCount());
                tr.td().text(o.getQuantity());
                tr.td().text(o.getTotal());
                ref(tr.td(), o.getPhase());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
