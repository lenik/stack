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

import com.bee32.zebra.erp.fab.FabOpCode;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class FabOpCodeManagerVbo
        extends Zc3Template_CEM<FabOpCodeManager, FabOpCode> {

    public FabOpCodeManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabOpCodeManager.class);
        formStruct = new FabOpCode().getFormStruct();
        setIndexFields("id", "code", "label", "description", "creationTime", "lastModified");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FabOpCodeManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        FabOpCodeManager manager = ref.get();
        FabOpCodeMapper mapper = manager.getMapper();
        List<FabOpCode> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (FabOpCode o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            tr.td().text(o.getId()).class_("col-id");
            stdcols(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}