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

import com.bee32.zebra.erp.fab.FabStep;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class FabStepManagerVbo
        extends Zc3Template_CEM<FabStepManager, FabStep> {

    public FabStepManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabStepManager.class);
        formDef = new FabStep().getFormDef();
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FabStepManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        FabStepMapper mapper = ctx.query(FabStepMapper.class);
        List<FabStep> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (FabStep o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
