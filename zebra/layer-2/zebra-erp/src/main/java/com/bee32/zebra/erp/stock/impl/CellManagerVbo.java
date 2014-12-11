package com.bee32.zebra.erp.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.stock.Cell;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class CellManagerVbo
        extends Zc3Template_CEM<CellManager, Cell> {

    public CellManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(CellManager.class);
        formStruct = new Cell().getFormStruct();
        setIndexFields("id", "label", "description", "creationTime", "lastModified");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<CellManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        CellManager manager = ref.get();
        CellMapper mapper = manager.getMapper();
        List<Cell> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Cell o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            tr.td().text(o.getId()).class_("col-id");
            stdcols(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
