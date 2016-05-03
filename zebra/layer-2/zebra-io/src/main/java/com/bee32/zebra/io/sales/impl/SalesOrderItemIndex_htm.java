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
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class SalesOrderItemIndex_htm
        extends SlimIndex_htm<SalesOrderItemIndex, SalesOrderItem, SalesOrderItemMask> {

    public static final String[] FIELDS = { "artifact", "altLabel", "altSpec", "quantity", "artifact.uom", "price",
            "total",
            // "beginDate", "endDate",
            "comment", "footnote" };

    public SalesOrderItemIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderItemIndex.class);
        indexFields.parse("i*s", FIELDS);
    }

    @Override
    protected SalesOrderItemMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        SalesOrderItemMask mask = VarMapState.restoreFrom(new SalesOrderItemMask(), ctx.getRequest());
        return mask;
    }

    @Override
    public List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<SalesOrderItemIndex> ref)
            throws ViewBuilderException, IOException {
        SalesOrderItemMapper mapper = ctx.query(SalesOrderItemMapper.class);
        SalesOrderItemMask mask = ctx.query(SalesOrderItemMask.class);
        List<SalesOrderItem> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        itab.addDetailFields("footnote");
        HtmlTbody tbody = itab.buildViewStart(out);

        for (SalesOrderItem o : list) {
            Artifact art = o.getArtifact();
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);

            ref(tr.td(), art).class_("small");
            tr.td().text(o.getAltLabel()).class_("small");
            tr.td().text(o.getAltSpec()).class_("small");
            tr.td().text(o.getQuantity());
            ref(tr.td(), art == null ? null : art.getUom());
            tr.td().text(o.getPrice());
            tr.td().text(o.getTotal());
            tr.td().text(o.getComment()).class_("small");
            tr.td().text(o.getFootnote()).class_("small");

            itab.cocols("s", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

    @Override
    protected void sections(IHtmlViewContext ctx, IHtmlOut out, IUiRef<SalesOrderItemIndex> ref)
            throws ViewBuilderException, IOException {
        // No section.
    }

}
