package com.bee32.zebra.io.sales.impl;

import java.io.IOException;

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
        SalesOrder sdoc = ref.get();

        for (SalesOrderItem item : sdoc.getItems()) {
            HtmlDivTag div = out.div().id("item-" + item.getId()).class_("zu-item");

            out.hr();
        }

        return out;
    }

}
