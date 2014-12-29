package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.t.pojo.Pair;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class UOMManagerVbo
        extends Zc3Template_CEM<UOMManager, UOM> {

    public UOMManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(UOMManager.class);
        formStruct = new UOM().getFormStruct();
        insertIndexFields("i*", "code", "label", "description", "property");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<UOMManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        UOMMapper mapper = ctx.query(UOMMapper.class);
        UOMCriteria criteria = criteriaFromRequest(new UOMCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(p.mainCol, "s-filter");
        {
            SwitchOverride<String> so;
            so = filters.switchPairs("属性", true, //
                    Pair.convertList("数量", "长度", "质量"), //
                    "property", criteria.property, criteria.noProperty);
            criteria.property = so.key;
            criteria.noProperty = so.isNull;
        }

        List<UOM> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (UOM o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("icu", tr, o);
            tr.td().text(o.getProperty());
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }
}
