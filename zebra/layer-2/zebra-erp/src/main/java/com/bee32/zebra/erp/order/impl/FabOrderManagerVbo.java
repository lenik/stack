package com.bee32.zebra.erp.order.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.order.FabOrder;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.security.User;

public class FabOrderManagerVbo
        extends Zc3Template_CEM<FabOrderManager, FabOrder> {

    public FabOrderManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(FabOrderManager.class);
        formStruct = new FabOrder().getFormStruct();
        setIndexFields("id", "label", "description", "creationTime", "lastModified");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FabOrderManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        FabOrderManager manager = ref.get();
        FabOrderMapper mapper = manager.getMapper();
        List<FabOrder> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (FabOrder o : list) {
            User op = o.getOp();

            HtmlTrTag tr = indexTable.tbody.tr();
            tr.td().text(o.getId()).class_("col-id");
            tr.td().text(op == null ? "" : op.getFullName()).align("center");
            tr.td().b().text(o.getLabel()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getDescription(), 50)).class_("small").style("max-width: 30em");
            // tr.td().text(o.getValue());
            stdcols(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
