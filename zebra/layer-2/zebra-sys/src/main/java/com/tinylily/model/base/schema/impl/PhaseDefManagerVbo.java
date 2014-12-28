package com.tinylily.model.base.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.PhaseDef;

public class PhaseDefManagerVbo
        extends Zc3Template_CEM<PhaseDefManager, PhaseDef> {

    public PhaseDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(PhaseDefManager.class);
        formStruct = new PhaseDef().getFormStruct();
        insertIndexFields("schema", "code", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<PhaseDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        PhaseDefManager manager = ref.get();
        PhaseDefMapper mapper = manager.getMapper();
        List<PhaseDef> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (PhaseDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getSchema().getLabel());
            stdcolsCLD(tr, o);
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
