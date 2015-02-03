package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class ArtifactCategoryIndexVbo
        extends SlimIndex_htm<ArtifactCategoryIndex, ArtifactCategory> {

    public ArtifactCategoryIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactCategoryIndex.class);
        indexFields.parse("i*sa", "label", "description", "depth", "parent");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<ArtifactCategory> a,
            IUiRef<ArtifactCategoryIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ArtifactCategoryMapper mapper = ctx.query(ArtifactCategoryMapper.class);
        List<ArtifactCategory> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (ArtifactCategory o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("u", tr, o);
                tr.td().text(o.getDepth());
                ref(tr.td(), o.getParent());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
