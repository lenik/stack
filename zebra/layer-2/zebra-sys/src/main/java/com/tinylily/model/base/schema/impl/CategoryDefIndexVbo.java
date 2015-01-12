package com.tinylily.model.base.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.CategoryDef;

public class CategoryDefIndexVbo
        extends Zc3Template_CEM<CategoryDefIndex, CategoryDef> {

    public CategoryDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(CategoryDefIndex.class);
        insertIndexFields("i*sa", "schema", "code", "label", "description", "refCount");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<CategoryDef> a, IUiRef<CategoryDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        CategoryDefMapper mapper = ctx.query(CategoryDefMapper.class);
        List<CategoryDef> list = a.noList() ? null : postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (CategoryDef o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                tr.td().text(o.getSchema().getLabel());
                cocols("cu", tr, o);
                tr.td().text(o.getRefCount());
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}