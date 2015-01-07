package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class SalesOrderManagerVbo
        extends Zc3Template_CEM<SalesOrderManager, SalesOrder> {

    public SalesOrderManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderManager.class);
        insertIndexFields("i*sa", "subject", "text", "topic", "org", "person", "phase", "total");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<SalesOrder> a, IUiRef<SalesOrderManager> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrderMapper mapper = ctx.query(SalesOrderMapper.class);
        List<SalesOrder> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (SalesOrder o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                cocols("m", tr, o);
                ref(tr.td(), o.getTopic());
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getPerson());
                ref(tr.td(), o.getPhase());
                tr.td().text(o.getTotal());
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
