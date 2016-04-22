package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.bas.ui.model.action.Location;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.io.art.SupplyMethod;
import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.Listing;
import com.bee32.zebra.tk.util.MaskBuilder;

public class ArtifactIndex_htm
        extends SlimIndex_htm<ArtifactIndex, Artifact, ArtifactMask> {

    public ArtifactIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactIndex.class);
        indexFields.parse("i*sa", "skuCode", "category", "label", "description", "uom", "supplyMethod", "barCode");
    }

    /**
     * 打印条码
     * 
     * @cmd.href ?view:=barcode
     */
    @Location(ZpCmds1Toolbar.class)
    public static class PrintBarcodeAction
            extends UiScriptAction {
    }

    @Override
    protected ArtifactMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ArtifactMask mask = MaskBuilder.fromRequest(new ArtifactMask(), ctx.getRequest());

        SwitcherModel<Integer> sw1;
        sw1 = switchers.entityOf("分类", false, //
                ctx.query(ArtifactCategoryMapper.class).filter(ArtifactCategoryMask.below(1)), //
                "cat", mask.categoryId, mask.noCategory);
        mask.categoryId = sw1.getSelection1();
        mask.noCategory = sw1.isSelectNull();

        SwitcherModel<SupplyMethod> sw2;
        sw2 = switchers.entryOf("供应方式", true, //
                Listing.pairsValLabel(SupplyMethod.METADATA.geLocalValues()), //
                "supply", mask.supplyMethod, mask.noSupplyMethod);
        mask.supplyMethod = sw2.getSelection1();
        mask.noSupplyMethod = sw2.isSelectNull();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<ArtifactIndex> ref)
            throws ViewBuilderException, IOException {
        ArtifactMapper mapper = ctx.query(ArtifactMapper.class);
        ArtifactMask mask = ctx.query(ArtifactMask.class);
        List<Artifact> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Artifact o : list) {
            ArtifactCategory category = o.getCategory();
            UOM uom = o.getUom();

            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getSkuCode());
            tr.td().text(category == null ? null : category.getLabel());
            itab.cocols("u", tr, o);
            tr.td().text(uom == null ? null : uom.getLabel() + "/" + o.getUomProperty());
            tr.td().text(o.getSupplyMethod().getLabel());
            tr.td().text(o.getBarCode());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }
}
