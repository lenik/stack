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
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

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
        DeliveryItemMask mask = VarMapState.restoreFrom(new DeliveryItemMask(), ctx.getRequest());
        return mask;
    }

    @Override
    public List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<DeliveryItemIndex> ref)
            throws ViewBuilderException, IOException {
        DeliveryItemMapper mapper = ctx.query(DeliveryItemMapper.class);
        DeliveryItemMask mask = ctx.query(DeliveryItemMask.class);
        List<DeliveryItem> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (DeliveryItem o : list) {
            SalesOrderItem b = o.getSalesOrderItem();
            Artifact art = b.getArtifact();

            HtmlTr tr = tbody.tr();
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
        itab.buildViewEnd(tbody);
        return list;
    }

    @Override
    protected void sections(IHtmlViewContext ctx, IHtmlOut out, IUiRef<DeliveryItemIndex> ref)
            throws ViewBuilderException, IOException {
        // No section.
    }

}
