package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.tinylily.model.base.schema.PriorityDef;
import com.tinylily.model.base.schema.impl.PriorityDefCriteria;
import com.tinylily.model.base.schema.impl.PriorityDefMapper;

public class PriorityDefIndexVbo
        extends SlimIndex_htm<PriorityDefIndex, PriorityDef, PriorityDefCriteria> {

    public PriorityDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(PriorityDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description");
    }

    @Override
    protected PriorityDefCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        PriorityDefCriteria criteria = fn.criteriaFromRequest(new PriorityDefCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<PriorityDef> a, IUiRef<PriorityDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        PriorityDefMapper mapper = ctx.query(PriorityDefMapper.class);
        PriorityDefCriteria criteria = ctx.query(PriorityDefCriteria.class);
        List<PriorityDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

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
