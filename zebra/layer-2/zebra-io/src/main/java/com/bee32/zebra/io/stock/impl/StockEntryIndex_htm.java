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

import com.bee32.zebra.io.stock.StockEntry;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class StockEntryIndex_htm
        extends SlimIndex_htm<StockEntryIndex, StockEntry, StockEntryCriteria> {

    public StockEntryIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(StockEntryIndex.class);
        indexFields.parse("i*sa", "label", "description");
    }

    @Override
    protected StockEntryCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        StockEntryCriteria criteria = CriteriaBuilder.fromRequest(new StockEntryCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<StockEntry> a, IUiRef<StockEntryIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        StockEntryMapper mapper = ctx.query(StockEntryMapper.class);
        StockEntryCriteria criteria = ctx.query(StockEntryCriteria.class);
        List<StockEntry> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (StockEntry o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("u", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
