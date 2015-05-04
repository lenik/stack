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
import net.bodz.lily.model.base.schema.AttributeDef;
import net.bodz.lily.model.base.schema.impl.AttributeDefCriteria;
import net.bodz.lily.model.base.schema.impl.AttributeDefMapper;
import net.bodz.lily.model.base.schema.impl.SchemaDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class AttributeDefIndexVbo
        extends SlimIndex_htm<AttributeDefIndex, AttributeDef, AttributeDefCriteria> {

    public AttributeDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(AttributeDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description", "refCount");
    }

    @Override
    protected AttributeDefCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        AttributeDefCriteria criteria = CriteriaBuilder.fromRequest(new AttributeDefCriteria(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("模式", false, //
                ctx.query(SchemaDefMapper.class).all(), //
                "schema", criteria.schemaId, false);
        criteria.schemaId = sw.getSelection1();

        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<AttributeDef> a, IUiRef<AttributeDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        AttributeDefMapper mapper = ctx.query(AttributeDefMapper.class);
        AttributeDefCriteria criteria = ctx.query(AttributeDefCriteria.class);
        List<AttributeDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (AttributeDef o : list) {
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
