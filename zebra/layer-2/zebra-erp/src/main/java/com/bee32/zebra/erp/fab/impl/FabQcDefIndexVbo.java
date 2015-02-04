package com.bee32.zebra.erp.fab.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.fab.FabQcDef;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class FabQcDefIndexVbo
        extends SlimIndex_htm<FabQcDefIndex, FabQcDef, FabQcDefCriteria> {

    public FabQcDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabQcDefIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected FabQcDefCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabQcDefCriteria criteria = fn.criteriaFromRequest(new FabQcDefCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<FabQcDef> a, IUiRef<FabQcDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FabQcDefMapper mapper = ctx.query(FabQcDefMapper.class);
        FabQcDefCriteria criteria = ctx.query(FabQcDefCriteria.class);
        List<FabQcDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (FabQcDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
