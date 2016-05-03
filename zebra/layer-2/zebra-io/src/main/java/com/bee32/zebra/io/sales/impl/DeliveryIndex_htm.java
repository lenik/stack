package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class DeliveryIndex_htm
        extends SlimIndex_htm<DeliveryIndex, Delivery, DeliveryMask> {

    public DeliveryIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(DeliveryIndex.class);
        indexFields.parse("i*sa", "salesOrder", "op", "org", "person", //
                "itemCount", "shipDest", "shipper", "shipmentId");
    }

    @Override
    protected DeliveryMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        DeliveryMask mask = VarMapState.restoreFrom(new DeliveryMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<DeliveryIndex> ref)
            throws ViewBuilderException, IOException {
        DeliveryMapper mapper = ctx.query(DeliveryMapper.class);
        DeliveryMask mask = ctx.query(DeliveryMask.class);
        List<Delivery> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Delivery o : list) {
            Contact shipDest = o.getShipDest();

            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            // stdcols("m", tr, o);
            ref(tr.td(), o.getSalesOrder());
            ref(tr.td(), o.getOp());
            ref(tr.td(), o.getOrg());
            ref(tr.td(), o.getPerson());
            tr.td().text(o.getItemCount());
            tr.td().text(shipDest == null ? null : shipDest.getFullAddress());
            ref(tr.td(), o.getShipper());
            tr.td().text(o.getShipmentId());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
