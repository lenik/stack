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

import com.bee32.zebra.erp.stock.Warehouse;
import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class WarehouseManagerVbo
        extends Zc3Template_CEM<WarehouseManager, Warehouse> {

    public WarehouseManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(WarehouseManager.class);
        formStruct = new Warehouse().getFormStruct();
        insertIndexFields("codeName", "label", "description", //
                "contact.fullAddress", "contact.tels");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<WarehouseManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        WarehouseManager manager = ref.get();
        WarehouseMapper mapper = manager.getMapper();
        List<Warehouse> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");

        for (Warehouse o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getCodeName());
            tr.td().text(o.getLabel());
            tr.td().text(o.getDescription()).class_("small");

            Contact contact = o.getContact();
            if (contact == null) {
                for (int i = 0; i < 2; i++)
                    tr.td();
            } else {
                tr.td().text(contact.getFullAddress()).class_("small");
                tr.td().text(contact.getTels());
            }
            stdcols1(tr, o);
        }

        dumpData(p.extradata, list);
        return ctx;
    }

}
