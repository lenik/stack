package com.bee32.zebra.io.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.stock.StockEntry;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class StockEntryManagerVbo
        extends Zc3Template_CEM<StockEntryManager, StockEntry> {

    public StockEntryManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(StockEntryManager.class);
        formStruct = new StockEntry().getFormStruct();
        insertIndexFields("label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<StockEntryManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        StockEntryManager manager = ref.get();
        StockEntryMapper mapper = manager.getMapper();
        List<StockEntry> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (StockEntry o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            stdcolsLD(tr, o);
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
