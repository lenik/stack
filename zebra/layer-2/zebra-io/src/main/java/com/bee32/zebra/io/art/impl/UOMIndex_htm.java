package com.bee32.zebra.io.art.impl;

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

import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.Listing;
import com.bee32.zebra.tk.util.MaskBuilder;

public class UOMIndex_htm
        extends SlimIndex_htm<UOMIndex, UOM, UOMMask> {

    public UOMIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(UOMIndex.class);
        indexFields.parse("i*", "code", "label", "description", "property");
    }

    @Override
    protected UOMMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        UOMMask mask = MaskBuilder.fromRequest(new UOMMask(), ctx.getRequest());

        SwitcherModel<String> sw;
        sw = switchers.entryOf("属性", true, //
                Listing.pairsValString("数量", "长度", "质量"), //
                "property", mask.property, mask.noProperty);
        mask.property = sw.getSelection1();
        mask.noProperty = sw.isSelectNull();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<UOMIndex> ref)
            throws ViewBuilderException, IOException {
        UOMMapper mapper = ctx.query(UOMMapper.class);
        UOMMask mask = ctx.query(UOMMask.class);
        List<UOM> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (UOM o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("icu", tr, o);
            tr.td().text(o.getProperty());
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
