package com.bee32.zebra.io.sales.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.site.IBasicSiteAnchors;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.slim.SlimPathFieldMap;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class Delivery_htm
        extends SlimMesgForm_htm<Delivery>
        implements IBasicSiteAnchors {

    public Delivery_htm() {
        super(Delivery.class);
    }

    @Override
    protected IHtmlOut beforeForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Delivery> ref)
            throws ViewBuilderException, IOException {
        out = super.beforeForm(ctx, out, ref);

        HttpServletRequest req = ctx.getRequest();
        String prompt = req.getParameter("prompt");
        if (prompt != null) {
            HtmlDiv alert = out.div().class_("alert alert-success");
            alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
            alert.iText(FA_INFO_CIRCLE, "fa");
            alert.bText("[提示]").text("这张送货单是刚刚生成的，但还没有保存，请在保存之前补充必要的信息。");
        }
        return out;
    }

    @Override
    protected IHtmlOut afterForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Delivery> ref)
            throws ViewBuilderException, IOException {
        Delivery delivery = ref.get();
        Integer id = delivery.getId();
        if (id == null)
            return out;

        SlimPathFieldMap itemIndexFields;
        {
            IType itemType = PotatoTypes.getInstance().forClass(DeliveryItem.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            itemIndexFields = new SlimPathFieldMap(itemFormDecl);
            try {
                itemIndexFields.parse("i*", DeliveryItemIndex_htm.FIELDS);
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        ItemsTable itab = new ItemsTable(ctx, itemIndexFields.values());
        itab.editorUrl = _webApp_ + "dlentry/ID/?view:=form&order.id=" + id;
        itab.ajaxUrl = "../../dlentry/data.json?doc=" + id;
        HtmlTbody tbody = itab.buildViewStart(out, "entries");
        itab.buildViewEnd(tbody);

        return out;
    }

    @Override
    protected Object persist(boolean create, IHtmlViewContext ctx, IHtmlOut out, IUiRef<Delivery> ref)
            throws Exception {
        Integer id = (Integer) super.persist(create, ctx, out, ref);

        Delivery obj = ref.get();
        if (!obj.getItems().isEmpty()) {
            DeliveryItemMapper itemMapper = ctx.query(DeliveryItemMapper.class);
            for (DeliveryItem item : obj.getItems())
                itemMapper.insert(item);
        }

        return id;
    }

}
