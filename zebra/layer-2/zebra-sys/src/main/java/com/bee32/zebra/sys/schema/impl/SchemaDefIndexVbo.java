package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;
import com.tinylily.model.base.schema.SchemaDef;
import com.tinylily.model.base.schema.impl.SchemaDefCriteria;
import com.tinylily.model.base.schema.impl.SchemaDefMapper;

public class SchemaDefIndexVbo
        extends SlimIndex_htm<SchemaDefIndex, SchemaDef, SchemaDefCriteria> {

    public SchemaDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(SchemaDefIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected SchemaDefCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        SchemaDefCriteria criteria = CriteriaBuilder.fromRequest(new SchemaDefCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<SchemaDef> a, IUiRef<SchemaDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        SchemaDefMapper mapper = ctx.query(SchemaDefMapper.class);
        SchemaDefCriteria criteria = ctx.query(SchemaDefCriteria.class);
        List<SchemaDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (SchemaDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("cu", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
