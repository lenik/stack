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

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class ArtifactManagerVbo
        extends Zc3Template_CEM<ArtifactManager, Artifact> {

    public ArtifactManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactManager.class);
        formStruct = new Artifact().getFormStruct();
        insertIndexFields("i*sa", "skuCode", "category", "label", "description", "uom", "supplyMethod", "barCode");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<ArtifactManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        ArtifactManager manager = ref.get();
        ArtifactMapper mapper = manager.getMapper();
        List<Artifact> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Artifact o : list) {
            ArtifactCategory category = o.getCategory();
            UOM uom = o.getUom();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getSkuCode());
            tr.td().text(category == null ? null : category.getLabel());
            cocols("u", tr, o);
            tr.td().text(uom == null ? null : uom.getLabel() + "/" + o.getUomProperty());
            tr.td().text(o.getSupplyMethod().name());
            tr.td().text(o.getBarCode());
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
