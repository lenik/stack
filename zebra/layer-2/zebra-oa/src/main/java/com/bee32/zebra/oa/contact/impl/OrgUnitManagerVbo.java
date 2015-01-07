package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.OrgUnit;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class OrgUnitManagerVbo
        extends Zc3Template_CEM<OrgUnitManager, OrgUnit> {

    public OrgUnitManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(OrgUnitManager.class);
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<OrgUnit> a, IUiRef<OrgUnitManager> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        OrgUnitMapper mapper = ctx.query(OrgUnitMapper.class);
        List<OrgUnit> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (OrgUnit o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
