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

import com.bee32.zebra.erp.stock.StockEvent;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class StockEventManagerVbo
        extends Zc3Template_CEM<StockEventManager, StockEvent> {

    public StockEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(StockEventManager.class);
        formStruct = new StockEvent().getFormStruct();
        insertIndexFields("label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<StockEventManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        StockEventManager manager = ref.get();
        StockEventMapper mapper = manager.getMapper();
        List<StockEvent> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (StockEvent o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            stdcolsLD(tr, o);
            stdcols1(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
