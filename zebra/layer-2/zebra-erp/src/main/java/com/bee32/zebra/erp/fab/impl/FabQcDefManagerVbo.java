package com.bee32.zebra.erp.fab.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.fab.FabQcDef;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class FabQcDefManagerVbo
        extends Zc3Template_CEM<FabQcDefManager, FabQcDef> {

    public FabQcDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabQcDefManager.class);
        formStruct = new FabQcDef().getFormStruct();
        insertIndexFields("code", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FabQcDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        FabQcDefManager manager = ref.get();
        FabQcDefMapper mapper = manager.getMapper();
        List<FabQcDef> list = mapper.all();

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (FabQcDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            stdcols1(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
