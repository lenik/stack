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
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class PlaceIndex_htm
        extends SlimIndex_htm<PlaceIndex, Place, PlaceMask> {

    public PlaceIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(PlaceIndex.class);
        indexFields.parse("i*sa", "usage", "label", "description", "position", "bbox", "party", "partyOrg");
    }

    @Override
    protected PlaceMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        PlaceMask mask = MaskBuilder.fromRequest(new PlaceMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Place> a, IUiRef<PlaceIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        PlaceMapper mapper = ctx.query(PlaceMapper.class);
        PlaceMask mask = ctx.query(PlaceMask.class);
        List<Place> list = a.noList() ? null : postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Place o : list) {
                Person person = o.getParty();
                Organization org = o.getPartyOrg();

                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getUsage().getLabel()).class_("small");
                itab.cocols("u", tr, o);
                tr.td().text(o.getPosition().isZero() ? null : o.getPosition().format(", "));
                tr.td().text(o.getBbox().isZero() ? null : o.getBbox().format("x"));
                tr.td().text(person == null ? null : person.getLabel());
                tr.td().text(org == null ? null : org.getLabel());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
