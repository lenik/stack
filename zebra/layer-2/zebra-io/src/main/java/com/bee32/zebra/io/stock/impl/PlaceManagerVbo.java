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

import com.bee32.zebra.io.stock.Place;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class PlaceManagerVbo
        extends Zc3Template_CEM<PlaceManager, Place> {

    public PlaceManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(PlaceManager.class);
        formStruct = new Place().getFormStruct();
        insertIndexFields("usage", "label", "description", "position", "bbox", "party", "partyOrg");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<PlaceManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        PlaceManager manager = ref.get();
        PlaceMapper mapper = manager.getMapper();
        List<Place> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Place o : list) {
            Person person = o.getParty();
            Organization org = o.getPartyOrg();

            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getUsage().name()).class_("small");
            stdcolsLD(tr, o);
            tr.td().text(o.getPosition().isZero() ? null : o.getPosition().format(", "));
            tr.td().text(o.getBbox().isZero() ? null : o.getBbox().format("x"));
            tr.td().text(person == null ? null : person.getLabel());
            tr.td().text(org == null ? null : org.getLabel());
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
