package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.SchemaDef;
import net.bodz.lily.model.base.schema.impl.SchemaDefMask;
import net.bodz.lily.model.base.schema.impl.SchemaDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class SchemaDefIndex_htm
        extends SlimIndex_htm<SchemaDefIndex, SchemaDef, SchemaDefMask> {

    public SchemaDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(SchemaDefIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected SchemaDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        SchemaDefMask mask = MaskBuilder.fromRequest(new SchemaDefMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<SchemaDef> a, IUiRef<SchemaDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        SchemaDefMapper mapper = ctx.query(SchemaDefMapper.class);
        SchemaDefMask mask = ctx.query(SchemaDefMask.class);
        List<SchemaDef> list = a.noList() ? null : postfilt(mapper.filter(mask));

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
