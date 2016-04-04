package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.PriorityDef;
import net.bodz.lily.model.base.schema.impl.PriorityDefMask;
import net.bodz.lily.model.base.schema.impl.PriorityDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class PriorityDefIndex_htm
        extends SlimIndex_htm<PriorityDefIndex, PriorityDef, PriorityDefMask> {

    public PriorityDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(PriorityDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description");
    }

    @Override
    protected PriorityDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        PriorityDefMask mask = MaskBuilder.fromRequest(new PriorityDefMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<PriorityDef> a, IUiRef<PriorityDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        PriorityDefMapper mapper = ctx.query(PriorityDefMapper.class);
        PriorityDefMask mask = ctx.query(PriorityDefMask.class);
        List<PriorityDef> list = a.noList() ? null : postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (PriorityDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getSchema().getLabel());
                itab.cocols("cu", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
