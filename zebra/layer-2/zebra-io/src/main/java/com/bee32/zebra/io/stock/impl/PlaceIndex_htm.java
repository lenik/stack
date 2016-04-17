package com.bee32.zebra.io.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.io.stock.Place;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
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
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<PlaceIndex> ref)
            throws ViewBuilderException, IOException {
        PlaceMapper mapper = ctx.query(PlaceMapper.class);
        PlaceMask mask = ctx.query(PlaceMask.class);
        List<Place> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Place o : list) {
            Person person = o.getParty();
            Organization org = o.getPartyOrg();

            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getUsage().getLabel()).class_("small");
            itab.cocols("u", tr, o);
            tr.td().text(o.getPosition().isZero() ? null : o.getPosition().format(", "));
            tr.td().text(o.getBbox().isZero() ? null : o.getBbox().format("x"));
            tr.td().text(person == null ? null : person.getLabel());
            tr.td().text(org == null ? null : org.getLabel());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
