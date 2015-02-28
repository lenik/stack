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

import com.bee32.zebra.erp.fab.FabStep;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class FabStepIndexVbo
        extends SlimIndex_htm<FabStepIndex, FabStep, FabStepCriteria> {

    public FabStepIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabStepIndex.class);
        indexFields.parse("i*sa", "label", "description");
    }

    @Override
    protected FabStepCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabStepCriteria criteria = fn.criteriaFromRequest(new FabStepCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<FabStep> a, IUiRef<FabStepIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FabStepMapper mapper = ctx.query(FabStepMapper.class);
        FabStepCriteria criteria = ctx.query(FabStepCriteria.class);
        List<FabStep> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (FabStep o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
