package com.bee32.zebra.erp.fab.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.fab.FabDevice;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class FabDeviceIndex_htm
        extends SlimIndex_htm<FabDeviceIndex, FabDevice, FabDeviceCriteria> {

    public FabDeviceIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(FabDeviceIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected FabDeviceCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FabDeviceCriteria criteria = CriteriaBuilder.fromRequest(new FabDeviceCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<FabDevice> a, IUiRef<FabDeviceIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FabDeviceMapper mapper = ctx.query(FabDeviceMapper.class);
        FabDeviceCriteria criteria = ctx.query(FabDeviceCriteria.class);
        List<FabDevice> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (FabDevice o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}