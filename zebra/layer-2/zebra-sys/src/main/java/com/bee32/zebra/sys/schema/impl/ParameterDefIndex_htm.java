package com.bee32.zebra.sys.schema.impl;

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
import net.bodz.lily.model.base.schema.ParameterDef;
import net.bodz.lily.model.base.schema.impl.ParameterDefMapper;
import net.bodz.lily.model.base.schema.impl.ParameterDefMask;
import net.bodz.lily.model.base.schema.impl.SchemaDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class ParameterDefIndex_htm
        extends SlimIndex_htm<ParameterDefIndex, ParameterDef, ParameterDefMask> {

    public ParameterDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(ParameterDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description", "refCount");
    }

    @Override
    protected ParameterDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ParameterDefMask mask = MaskBuilder.fromRequest(new ParameterDefMask(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("模式", false, //
                ctx.query(SchemaDefMapper.class).all(), //
                "schema", mask.schemaId, false);
        mask.schemaId = sw.getSelection1();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<ParameterDefIndex> ref)
            throws ViewBuilderException, IOException {
        ParameterDefMapper mapper = ctx.query(ParameterDefMapper.class);
        ParameterDefMask mask = ctx.query(ParameterDefMask.class);
        List<ParameterDef> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (ParameterDef o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getSchema().getLabel());
            itab.cocols("cu", tr, o);
            tr.td().text(o.getRefCount());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
