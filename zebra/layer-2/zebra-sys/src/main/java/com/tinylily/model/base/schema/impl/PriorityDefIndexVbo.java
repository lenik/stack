package com.tinylily.model.base.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.PriorityDef;

public class PriorityDefIndexVbo
        extends Zc3Template_CEM<PriorityDefIndex, PriorityDef> {

    public PriorityDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(PriorityDefIndex.class);
        insertIndexFields("i*sa", "schema", "code", "label", "description");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<PriorityDef> a, IUiRef<PriorityDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        PriorityDefMapper mapper = ctx.query(PriorityDefMapper.class);
        List<PriorityDef> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (PriorityDef o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                tr.td().text(o.getSchema().getLabel());
                cocols("cu", tr, o);
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
