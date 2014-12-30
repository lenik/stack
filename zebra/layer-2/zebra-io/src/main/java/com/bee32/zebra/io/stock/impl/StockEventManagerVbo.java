package com.bee32.zebra.io.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class StockEventManagerVbo
        extends Zc3Template_CEM<StockEventManager, StockEvent> {

    public StockEventManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(StockEventManager.class);
        formDef = new StockEvent().getFormDef();
        insertIndexFields("i*sa", "form", "category", "subject", "text", "org", "orgUnit", "person", "quantity", "total",
                "phase");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<StockEventManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        StockEventMapper mapper = ctx.query(StockEventMapper.class);
        List<StockEvent> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (StockEvent o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            ref(tr.td(), o.getForm());
            ref(tr.td(), o.getCategory());
            cocols("m", tr, o);
            ref(tr.td(), o.getOrg());
            ref(tr.td(), o.getOrgUnit());
            ref(tr.td(), o.getPerson());
            tr.td().text(o.getQuantity());
            tr.td().text(o.getTotal());
            ref(tr.td(), o.getPhase());
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
