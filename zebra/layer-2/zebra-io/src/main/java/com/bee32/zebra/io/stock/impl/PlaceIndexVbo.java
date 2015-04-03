package com.bee32.zebra.io.stock.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
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
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class PlaceIndexVbo
        extends SlimIndex_htm<PlaceIndex, Place, PlaceCriteria> {

    public PlaceIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(PlaceIndex.class);
        indexFields.parse("i*sa", "usage", "label", "description", "position", "bbox", "party", "partyOrg");
    }

    @Override
    protected PlaceCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        PlaceCriteria criteria = CriteriaBuilder.fromRequest(new PlaceCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<Place> a, IUiRef<PlaceIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        PlaceMapper mapper = ctx.query(PlaceMapper.class);
        PlaceCriteria criteria = ctx.query(PlaceCriteria.class);
        List<Place> list = a.noList() ? null : postfilt(mapper.filter(criteria));

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
