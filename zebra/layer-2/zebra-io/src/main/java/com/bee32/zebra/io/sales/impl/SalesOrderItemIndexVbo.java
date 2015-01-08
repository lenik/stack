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

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class SalesOrderItemIndexVbo
        extends Zc3Template_CEM<SalesOrderItemIndex, SalesOrderItem> {

    public SalesOrderItemIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderItemIndex.class);
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    public void buildDataView(IHtmlViewContext ctx, DataViewAnchors<SalesOrderItem> a,
            IUiRef<SalesOrderItemIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrderItemMapper mapper = ctx.query(SalesOrderItemMapper.class);

        SalesOrderItemCriteria criteria = criteriaFromRequest(new SalesOrderItemCriteria(), ctx.getRequest());
        List<SalesOrderItem> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (SalesOrderItem o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                cocols("u", tr, o);
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
