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

import com.bee32.zebra.erp.fab.FabOpCode;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class FabOpCodeIndexVbo
        extends Zc3Template_CEM<FabOpCodeIndex, FabOpCode> {

    public FabOpCodeIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabOpCodeIndex.class);
        insertIndexFields("i*sa", "code", "label", "description");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<FabOpCode> a, IUiRef<FabOpCodeIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FabOpCodeMapper mapper = ctx.query(FabOpCodeMapper.class);
        List<FabOpCode> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (FabOpCode o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
