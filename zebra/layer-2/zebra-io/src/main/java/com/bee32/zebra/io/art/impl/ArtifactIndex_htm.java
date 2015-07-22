package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.io.art.SupplyMethod;
import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;
import com.bee32.zebra.tk.util.Listing;

public class ArtifactIndex_htm
        extends SlimIndex_htm<ArtifactIndex, Artifact, ArtifactCriteria> {

    public ArtifactIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactIndex.class);
        indexFields.parse("i*sa", "skuCode", "category", "label", "description", "uom", "supplyMethod", "barCode");
    }

    @Override
    protected void titleInfo(IHttpViewContext ctx, IUiRef<ArtifactIndex> ref, boolean indexPage) {
        super.titleInfo(ctx, ref, indexPage);
        PageStruct page = new PageStruct(ctx.getHtmlDoc());
        if (indexPage) {
            page.cmds1.a().href("?view:=barcode").text("打印条码");
        }
    }

    @Override
    protected ArtifactCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ArtifactCriteria criteria = CriteriaBuilder.fromRequest(new ArtifactCriteria(), ctx.getRequest());

        SwitcherModel<Integer> sw1;
        sw1 = switchers.entityOf("分类", false, //
                ctx.query(ArtifactCategoryMapper.class).filter(ArtifactCategoryCriteria.below(1)), //
                "cat", criteria.categoryId, criteria.noCategory);
        criteria.categoryId = sw1.getSelection1();
        criteria.noCategory = sw1.isSelectNull();

        SwitcherModel<SupplyMethod> sw2;
        sw2 = switchers.entryOf("供应方式", true, //
                Listing.pairsValLabel(SupplyMethod.METADATA.geLocalValues()), //
                "supply", criteria.supplyMethod, criteria.noSupplyMethod);
        criteria.supplyMethod = sw2.getSelection1();
        criteria.noSupplyMethod = sw2.isSelectNull();

        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<Artifact> a, IUiRef<ArtifactIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        ArtifactMapper mapper = ctx.query(ArtifactMapper.class);
        ArtifactCriteria criteria = ctx.query(ArtifactCriteria.class);
        List<Artifact> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Artifact o : list) {
                ArtifactCategory category = o.getCategory();
                UOM uom = o.getUom();

                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getSkuCode());
                tr.td().text(category == null ? null : category.getLabel());
                itab.cocols("u", tr, o);
                tr.td().text(uom == null ? null : uom.getLabel() + "/" + o.getUomProperty());
                tr.td().text(o.getSupplyMethod().getLabel());
                tr.td().text(o.getBarCode());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }
}
