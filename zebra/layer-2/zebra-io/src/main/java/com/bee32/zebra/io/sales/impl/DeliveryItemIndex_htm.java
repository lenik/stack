package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class DeliveryItemIndex_htm
        extends SlimIndex_htm<DeliveryItemIndex, DeliveryItem, DeliveryItemMask> {

    public static final String[] FIELDS = { //
    // "delivery", //
            "salesOrderItem", //
            "salesOrderItem.artifact", "salesOrderItem.altLabel", "salesOrderItem.altSpec", //
            "quantity", "artifact.uom", // "salesOrderItem.altUom",
            "price", "total" };

    public DeliveryItemIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(DeliveryItemIndex.class);
        indexFields.parse("i*", FIELDS);
    }

    @Override
    protected DeliveryItemMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        DeliveryItemMask mask = MaskBuilder.fromRequest(new DeliveryItemMask(), ctx.getRequest());
        return mask;
    }

    @Override
    public void dataIndex(IHtmlViewContext ctx, DataViewAnchors<DeliveryItem> a, IUiRef<DeliveryItemIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        DeliveryItemMapper mapper = ctx.query(DeliveryItemMapper.class);
        DeliveryItemMask mask = ctx.query(DeliveryItemMask.class);
        List<DeliveryItem> list = a.noList() ? null : postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (DeliveryItem o : list) {
                SalesOrderItem b = o.getSalesOrderItem();
                Artifact art = b.getArtifact();

                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                // tr.td().text(o.getDelivery().getId());
                tr.td().text(o.getSalesOrderItem().getId());
                ref(tr.td(), art).class_("small");
                tr.td().text(b.getAltLabel()).class_("small");
                tr.td().text(b.getAltSpec()).class_("small");
                tr.td().text(o.getQuantity());
                ref(tr.td(), art == null ? null : art.getUom());
                tr.td().text(o.getPrice());
                tr.td().text(o.getTotal());

                // itab.cocols("s", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

    @Override
    protected void sections(IHtmlViewContext ctx, IHtmlTag out, IUiRef<DeliveryItemIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        // No section.
    }

}
