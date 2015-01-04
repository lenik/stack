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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.bee32.zebra.tk.util.Listing;

public class UOMManagerVbo
        extends Zc3Template_CEM<UOMManager, UOM> {

    public UOMManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(UOMManager.class);
        insertIndexFields("i*", "code", "label", "description", "property");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<UOMManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        UOMMapper mapper = ctx.query(UOMMapper.class);
        UOMCriteria criteria = criteriaFromRequest(new UOMCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(page.mainCol, "s-filter");
        {
            SwitchOverride<String> so;
            so = filters.switchPairs("属性", true, //
                    Listing.pairsValString("数量", "长度", "质量"), //
                    "property", criteria.property, criteria.noProperty);
            criteria.property = so.key;
            criteria.noProperty = so.isNull;
        }

        List<UOM> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");
        for (UOM o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("icu", tr, o);
            tr.td().text(o.getProperty());
        }

        dumpFullData(page.extradata, list);
    }

}
