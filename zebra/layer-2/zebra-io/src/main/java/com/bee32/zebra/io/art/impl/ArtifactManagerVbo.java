package com.bee32.zebra.io.art.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.io.art.SupplyMethod;
import com.bee32.zebra.io.art.UOM;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.bee32.zebra.tk.util.Listing;

public class ArtifactManagerVbo
        extends Zc3Template_CEM<ArtifactManager, Artifact> {

    public ArtifactManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(ArtifactManager.class);
        insertIndexFields("i*sa", "skuCode", "category", "label", "description", "uom", "supplyMethod", "barCode");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<ArtifactManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ArtifactMapper mapper = ctx.query(ArtifactMapper.class);
        ArtifactCriteria criteria = criteriaFromRequest(new ArtifactCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(page.mainCol, "s-filter");
        {
            SwitchOverride<Integer> so1;
            so1 = filters.switchEntity("分类", false, //
                    ctx.query(ArtifactCategoryMapper.class).filter(ArtifactCategoryCriteria.below(1)), //
                    "cat", criteria.categoryId, criteria.noCategory);
            criteria.categoryId = so1.key;
            criteria.noCategory = so1.isNull;

            SwitchOverride<SupplyMethod> so2;
            so2 = filters.switchPairs("供应方式", true, //
                    Listing.pairsValLabel(SupplyMethod.METADATA.geLocalValues()), //
                    "supply", criteria.supplyMethod, criteria.noSupplyMethod);
            criteria.supplyMethod = so2.key;
            criteria.noSupplyMethod = so2.isNull;
        }

        List<Artifact> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");
        for (Artifact o : list) {
            ArtifactCategory category = o.getCategory();
            UOM uom = o.getUom();

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getSkuCode());
            tr.td().text(category == null ? null : category.getLabel());
            cocols("u", tr, o);
            tr.td().text(uom == null ? null : uom.getLabel() + "/" + o.getUomProperty());
            tr.td().text(o.getSupplyMethod().getLabel());
            tr.td().text(o.getBarCode());
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
