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
import net.bodz.lily.model.base.schema.PhaseDef;
import net.bodz.lily.model.base.schema.impl.PhaseDefMask;
import net.bodz.lily.model.base.schema.impl.PhaseDefMapper;
import net.bodz.lily.model.base.schema.impl.SchemaDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class PhaseDefIndex_htm
        extends SlimIndex_htm<PhaseDefIndex, PhaseDef, PhaseDefMask> {

    public PhaseDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(PhaseDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description");
    }

    @Override
    protected PhaseDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        PhaseDefMask mask = MaskBuilder.fromRequest(new PhaseDefMask(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("模式", false, //
                ctx.query(SchemaDefMapper.class).all(), //
                "schema", mask.schemaId, false);
        mask.schemaId = sw.getSelection1();

        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<PhaseDef> a, IUiRef<PhaseDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        PhaseDefMapper mapper = ctx.query(PhaseDefMapper.class);
        PhaseDefMask mask = ctx.query(PhaseDefMask.class);
        List<PhaseDef> list = a.noList() ? null : postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (PhaseDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getSchema().getLabel());
                itab.cocols("cu", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
