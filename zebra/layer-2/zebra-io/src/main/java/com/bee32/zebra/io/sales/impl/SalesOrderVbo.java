package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.sea.FooMesgVbo;

public class SalesOrderVbo
        extends FooMesgVbo<SalesOrder> {

    public SalesOrderVbo() {
        super(SalesOrder.class);
    }

    @Override
    protected IHtmlTag afterForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<SalesOrder> ref, IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrderItemMapper itemMapper = ctx.query(SalesOrderItemMapper.class);

        SalesOrder sdoc = ref.get();

        SalesOrderItemCriteria criteria = new SalesOrderItemCriteria();
        criteria.salesOrderId = sdoc.getId();
        List<SalesOrderItem> items = itemMapper.filter(criteria);

        for (SalesOrderItem item : items) {
            HtmlDivTag div = out.div().id("item-" + item.getId()).class_("zu-item");

            out.hr();
        }

        return out;
    }

}
