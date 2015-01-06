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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class SalesOrderItemManagerVbo
        extends Zc3Template_CEM<SalesOrderItemManager, SalesOrderItem> {

    public SalesOrderItemManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderItemManager.class);
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    public void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<SalesOrderItemManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrderItemMapper mapper = ctx.query(SalesOrderItemMapper.class);

        SalesOrderItemCriteria criteria = criteriaFromRequest(new SalesOrderItemCriteria(), ctx.getRequest());

        List<SalesOrderItem> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");
        for (SalesOrderItem o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("u", tr, o);
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
