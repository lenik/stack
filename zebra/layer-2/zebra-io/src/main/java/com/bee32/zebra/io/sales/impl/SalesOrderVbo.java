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

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.ItemsTable;
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
        if (sdoc.getId() == null)
            return out;

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
                itemIndexFields.parse("i*s", "artifact", "altLabel", "altSpec", "quantity", "artifact.uom", "price",
                        "total",
                        // "beginDate", "endDate",
                        "comment", "footnote");
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        ItemsTable xtab = new ItemsTable(out);
        xtab.ajaxUrl = "../../sentry/data.json?doc=" + sdoc.getId();

        xtab.buildHeader(itemIndexFields.values());

        for (SalesOrderItem o : sdoc.getItems()) {
            Artifact art = o.getArtifact();

            HtmlTrTag tr = xtab.tbody.tr();
            xtab.cocols("i", tr, o);
            ref(tr.td(), art).class_("small");
            tr.td().text(o.getAltLabel());
            tr.td().text(o.getAltSpec());
            tr.td().text(o.getQuantity());
            ref(tr.td(), art == null ? null : art.getUom());
            tr.td().text(o.getPrice());
            tr.td().text(o.getTotal());
            tr.td().text(o.getComment());
            tr.td().text(o.getFootnote());
            xtab.cocols("s", tr, o);
        }

        return out;
    }

}
