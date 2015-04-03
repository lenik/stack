package com.bee32.zebra.erp.fab.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class FabStepDefIndexVbo
        extends SlimIndex_htm<FabStepDefIndex, FabStepDef, FabStepDefCriteria> {

    public FabStepDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabStepDefIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected FabStepDefCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabStepDefCriteria criteria = CriteriaBuilder.fromRequest(new FabStepDefCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<FabStepDef> a, IUiRef<FabStepDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FabStepDefMapper mapper = ctx.query(FabStepDefMapper.class);
        FabStepDefCriteria criteria = ctx.query(FabStepDefCriteria.class);
        List<FabStepDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());

        if (a.dataList())
            for (FabStepDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
