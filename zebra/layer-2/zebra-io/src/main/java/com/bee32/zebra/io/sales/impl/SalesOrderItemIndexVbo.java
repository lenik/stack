package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class SalesOrderItemIndexVbo
        extends SlimIndex_htm<SalesOrderItemIndex, SalesOrderItem, SalesOrderItemCriteria> {

    public static final String[] FIELDS = { "artifact", "altLabel", "altSpec", "quantity", "artifact.uom", "price",
            "total",
            // "beginDate", "endDate",
            "comment", "footnote" };

    public SalesOrderItemIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(SalesOrderItemIndex.class);
        indexFields.parse("i*s", FIELDS);
    }

    @Override
    protected SalesOrderItemCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        SalesOrderItemCriteria criteria = CriteriaBuilder.fromRequest(new SalesOrderItemCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    public void dataIndex(IHttpViewContext ctx, DataViewAnchors<SalesOrderItem> a, IUiRef<SalesOrderItemIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrderItemMapper mapper = ctx.query(SalesOrderItemMapper.class);
        SalesOrderItemCriteria criteria = ctx.query(SalesOrderItemCriteria.class);
        List<SalesOrderItem> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.addDetailFields("footnote");
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (SalesOrderItem o : list) {
                Artifact art = o.getArtifact();
                HtmlTrTag tr = itab.tbody.tr();
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

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

    @Override
    protected void sections(IHttpViewContext ctx, IHtmlTag out, IUiRef<SalesOrderItemIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        // No section.
    }

}
