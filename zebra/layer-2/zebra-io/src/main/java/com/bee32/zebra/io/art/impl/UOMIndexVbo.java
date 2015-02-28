package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.Listing;

public class UOMIndexVbo
        extends SlimIndex_htm<UOMIndex, UOM, UOMCriteria> {

    public UOMIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(UOMIndex.class);
        indexFields.parse("i*", "code", "label", "description", "property");
    }

    @Override
    protected UOMCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        UOMCriteria criteria = fn.criteriaFromRequest(new UOMCriteria(), ctx.getRequest());

        SwitcherModel<String> sw;
        sw = switchers.entryOf("属性", true, //
                Listing.pairsValString("数量", "长度", "质量"), //
                "property", criteria.property, criteria.noProperty);
        criteria.property = sw.getSelection1();
        criteria.noProperty = sw.isSelectNull();

        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<UOM> a, IUiRef<UOMIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        UOMMapper mapper = ctx.query(UOMMapper.class);
        UOMCriteria criteria = ctx.query(UOMCriteria.class);
        List<UOM> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (UOM o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("icu", tr, o);
                tr.td().text(o.getProperty());
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
