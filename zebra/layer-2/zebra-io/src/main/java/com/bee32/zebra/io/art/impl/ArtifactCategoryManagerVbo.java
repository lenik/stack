package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class ArtifactCategoryManagerVbo
        extends Zc3Template_CEM<ArtifactCategoryManager, ArtifactCategory> {

    public ArtifactCategoryManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactCategoryManager.class);
        formStruct = new ArtifactCategory().getFormStruct();
        insertIndexFields("i*sa", "label", "description", "depth", "parent");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<ArtifactCategoryManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ctx = super.buildHtmlView(ctx, ref, options);

        PageStruct p = new PageStruct(ctx);

        ArtifactCategoryMapper mapper = ctx.query(ArtifactCategoryMapper.class);
        List<ArtifactCategory> list = mapper.all();

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (ArtifactCategory o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("u", tr, o);
            tr.td().text(o.getDepth());
            ref(tr.td(), o.getParent());
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
