package com.bee32.zebra.io.sales.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.IndexTable;
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

        PathFieldMap itemIndexFields;
        {
            IType itemType = PotatoTypes.getInstance().forClass(SalesOrderItem.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            itemIndexFields = new PathFieldMap(itemFormDecl);
            try {
                itemIndexFields.parse("i*sa", "artifact", "altLabel", "altSpec", "quantity", "price", "comment",
                        "footnote");
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        IndexTable itab = new IndexTable(out, "items");
        itab.attr("dom", "rti");
        itab.attr("no-colvis", "1");
        itab.setAjaxMode(false);
        itab.setFootColumns(false);
        itab.buildHeader(ctx, itemIndexFields.values());

        for (SalesOrderItem o : sdoc.getItems()) {
            HtmlTrTag tr = itab.tbody.tr();
            itab.cocols("i", tr, o);
            ref(tr.td(), o.getArtifact()).class_("small");
            tr.td().text(o.getAltLabel());
            tr.td().text(o.getAltSpec());
            tr.td().text(o.getQuantity());
            tr.td().text(o.getPrice());
            tr.td().text(o.getComment());
            tr.td().text(o.getFootnote());
            itab.cocols("sa", tr, o);
        }
        return out;
    }

}
