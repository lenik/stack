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

import com.bee32.zebra.erp.fab.FabStepDef;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class FabStepDefIndex_htm
        extends SlimIndex_htm<FabStepDefIndex, FabStepDef, FabStepDefMask> {

    public FabStepDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(FabStepDefIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected FabStepDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabStepDefMask mask = MaskBuilder.fromRequest(new FabStepDefMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<FabStepDef> a, IUiRef<FabStepDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FabStepDefMapper mapper = ctx.query(FabStepDefMapper.class);
        FabStepDefMask mask = ctx.query(FabStepDefMask.class);
        List<FabStepDef> list = a.noList() ? null : postfilt(mapper.filter(mask));

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
