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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class FabOpCodeManagerVbo
        extends Zc3Template_CEM<FabOpCodeManager, FabOpCode> {

    public FabOpCodeManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabOpCodeManager.class);
        formDef = new FabOpCode().getFormDef();
        insertIndexFields("i*sa", "code", "label", "description");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<FabOpCodeManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        FabOpCodeMapper mapper = ctx.query(FabOpCodeMapper.class);
        List<FabOpCode> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(page.mainCol, "list");
        for (FabOpCode o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
