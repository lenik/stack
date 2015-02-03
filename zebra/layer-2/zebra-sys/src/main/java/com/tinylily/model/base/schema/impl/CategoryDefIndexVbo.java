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

import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.tinylily.model.base.schema.CategoryDef;

public class CategoryDefIndexVbo
        extends SlimIndex_htm<CategoryDefIndex, CategoryDef> {

    public CategoryDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(CategoryDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description", "refCount");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<CategoryDef> a, IUiRef<CategoryDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        CategoryDefMapper mapper = ctx.query(CategoryDefMapper.class);

        CategoryDefCriteria criteria = fn.criteriaFromRequest(new CategoryDefCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("模式", false, //
                    ctx.query(SchemaDefMapper.class).all(), //
                    "schema", criteria.schemaId, false);
            criteria.schemaId = so.key;
        }

        List<CategoryDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (CategoryDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getSchema().getLabel());
                itab.cocols("cu", tr, o);
                tr.td().text(o.getRefCount());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
