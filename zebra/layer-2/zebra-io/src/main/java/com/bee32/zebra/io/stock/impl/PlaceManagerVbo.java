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
        formDef = new Place().getFormDef();
        insertIndexFields("i*sa", "usage", "label", "description", "position", "bbox", "party", "partyOrg");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<PlaceManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        PlaceMapper mapper = ctx.query(PlaceMapper.class);
        List<Place> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(page.mainCol, "list");
        for (Place o : list) {
            Person person = o.getParty();
            Organization org = o.getPartyOrg();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getUsage().getLabel()).class_("small");
            cocols("u", tr, o);
            tr.td().text(o.getPosition().isZero() ? null : o.getPosition().format(", "));
            tr.td().text(o.getBbox().isZero() ? null : o.getBbox().format("x"));
            tr.td().text(person == null ? null : person.getLabel());
            tr.td().text(org == null ? null : org.getLabel());
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
