package com.bee32.zebra.erp.fab.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.erp.fab.FabStep;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class FabStepIndex_htm
        extends SlimIndex_htm<FabStepIndex, FabStep, FabStepMask> {

    public FabStepIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(FabStepIndex.class);
        indexFields.parse("i*sa", "label", "description");
    }

    @Override
    protected FabStepMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabStepMask mask = MaskBuilder.fromRequest(new FabStepMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FabStepIndex> ref)
            throws ViewBuilderException, IOException {
        FabStepMapper mapper = ctx.query(FabStepMapper.class);
        FabStepMask mask = ctx.query(FabStepMask.class);
        List<FabStep> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (FabStep o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
