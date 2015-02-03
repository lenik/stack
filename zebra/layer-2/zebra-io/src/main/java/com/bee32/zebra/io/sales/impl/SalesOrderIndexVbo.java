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
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class SalesOrderIndexVbo
        extends SlimIndex_htm<SalesOrderIndex, SalesOrder> {

    public SalesOrderIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderIndex.class);
        indexFields.parse("i*sa", "subject", "text", "topic", "org", "person", "phase", "total");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<SalesOrder> a, IUiRef<SalesOrderIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrderMapper mapper = ctx.query(SalesOrderMapper.class);
        List<SalesOrder> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (SalesOrder o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("m", tr, o);
                ref(tr.td(), o.getTopic());
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getPerson());
                ref(tr.td(), o.getPhase());
                tr.td().text(o.getTotal());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
