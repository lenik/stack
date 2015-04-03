package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.OrgUnit;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class OrgUnitIndexVbo
        extends SlimIndex_htm<OrgUnitIndex, OrgUnit, OrgUnitCriteria> {

    public OrgUnitIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(OrgUnitIndex.class);
        indexFields.parse("i*sa", "label", "description");
    }

    @Override
    protected OrgUnitCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        OrgUnitCriteria criteria = CriteriaBuilder.fromRequest(new OrgUnitCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<OrgUnit> a, IUiRef<OrgUnitIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        OrgUnitMapper mapper = ctx.query(OrgUnitMapper.class);
        OrgUnitCriteria criteria = ctx.query(OrgUnitCriteria.class);
        List<OrgUnit> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (OrgUnit o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
