package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class SalesOrderIndex_htm
        extends SlimIndex_htm<SalesOrderIndex, SalesOrder, SalesOrderMask> {

    public SalesOrderIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderIndex.class);
        indexFields.parse("i*sa", "subject", "text", "topic", "org", "person", "phase", "itemCount", "total");
    }

    @Override
    protected SalesOrderMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        SalesOrderMask mask = MaskBuilder.fromRequest(new SalesOrderMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<SalesOrderIndex> ref)
            throws ViewBuilderException, IOException {
        SalesOrderMapper mapper = ctx.query(SalesOrderMapper.class);
        SalesOrderMask mask = ctx.query(SalesOrderMask.class);
        List<SalesOrder> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (SalesOrder o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            itab.cocols("m", tr, o);
            ref(tr.td(), o.getTopic());
            ref(tr.td(), o.getOrg());
            ref(tr.td(), o.getPerson());
            ref(tr.td(), o.getPhase());
            tr.td().text(o.getItemCount());
            tr.td().text(o.getTotal());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
