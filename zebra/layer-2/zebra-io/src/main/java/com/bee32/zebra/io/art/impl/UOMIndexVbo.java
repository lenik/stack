package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.bee32.zebra.tk.util.Listing;

public class UOMIndexVbo
        extends Zc3Template_CEM<UOMIndex, UOM> {

    public UOMIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(UOMIndex.class);
        indexFields.parse("i*", "code", "label", "description", "property");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<UOM> a, IUiRef<UOMIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        UOMMapper mapper = ctx.query(UOMMapper.class);
        UOMCriteria criteria = fn.criteriaFromRequest(new UOMCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<String> so;
            so = filters.switchPairs("属性", true, //
                    Listing.pairsValString("数量", "长度", "质量"), //
                    "property", criteria.property, criteria.noProperty);
            criteria.property = so.key;
            criteria.noProperty = so.isNull;
        }

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
