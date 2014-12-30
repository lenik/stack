package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class DeliveryManagerVbo
        extends Zc3Template_CEM<DeliveryManager, Delivery> {

    public DeliveryManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(DeliveryManager.class);
        formDef = new Delivery().getFormDef();
        insertIndexFields("i*sa", "salesOrder", "op", "org", "person", "shipDest", "shipper", "shipmentId");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<DeliveryManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        DeliveryMapper mapper = ctx.query(DeliveryMapper.class);
        List<Delivery> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(page.mainCol, "list");
        for (Delivery o : list) {
            Contact shipDest = o.getShipDest();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            // stdcols("m", tr, o);
            ref(tr.td(), o.getSalesOrder());
            ref(tr.td(), o.getOp());
            ref(tr.td(), o.getOrg());
            ref(tr.td(), o.getPerson());
            tr.td().text(shipDest == null ? null : shipDest.getFullAddress());
            ref(tr.td(), o.getShipper());
            tr.td().text(o.getShipmentId());
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
