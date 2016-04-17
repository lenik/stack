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

import com.bee32.zebra.erp.fab.FabOpCode;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class FabOpCodeIndex_htm
        extends SlimIndex_htm<FabOpCodeIndex, FabOpCode, FabOpCodeMask> {

    public FabOpCodeIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(FabOpCodeIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected FabOpCodeMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabOpCodeMask mask = MaskBuilder.fromRequest(new FabOpCodeMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FabOpCodeIndex> ref)
            throws ViewBuilderException, IOException {
        FabOpCodeMapper mapper = ctx.query(FabOpCodeMapper.class);
        FabOpCodeMask mask = ctx.query(FabOpCodeMask.class);
        List<FabOpCode> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (FabOpCode o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
