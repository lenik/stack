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

import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class ArtifactCategoryIndex_htm
        extends SlimIndex_htm<ArtifactCategoryIndex, ArtifactCategory, ArtifactCategoryMask> {

    public ArtifactCategoryIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactCategoryIndex.class);
        indexFields.parse("i*sa", "label", "description", "depth", "parent");
    }

    @Override
    protected ArtifactCategoryMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ArtifactCategoryMask mask = MaskBuilder.fromRequest(new ArtifactCategoryMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<ArtifactCategoryIndex> ref)
            throws ViewBuilderException, IOException {
        ArtifactCategoryMapper mapper = ctx.query(ArtifactCategoryMapper.class);
        ArtifactCategoryMask mask = ctx.query(ArtifactCategoryMask.class);
        List<ArtifactCategory> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (ArtifactCategory o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            itab.cocols("u", tr, o);
            tr.td().text(o.getDepth());
            ref(tr.td(), o.getParent());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
