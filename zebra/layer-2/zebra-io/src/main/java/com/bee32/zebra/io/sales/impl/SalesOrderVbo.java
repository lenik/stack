package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.req.MethodNames;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.hbin.SplitForm;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;
import com.bee32.zebra.tk.util.DateUtils;
import com.bee32.zebra.tk.util.DoubleUtils;

public class SalesOrderVbo
        extends SlimMesgForm_htm<SalesOrder> {

    public SalesOrderVbo() {
        super(SalesOrder.class);
    }

    @Override
    protected boolean buildBasicGroup(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException {
        super.buildBasicGroup(ctx, out, instanceRef, group, options);

        SalesOrder sdoc = (SalesOrder) instanceRef.get();
        if (sdoc.getId() != null) {
            SplitForm form = (SplitForm) out.getParent();
            HtmlATag link = form.head.a().class_("fa").href("#s-delivery").text(FA_ANGLE_DOUBLE_RIGHT);
            int n = sdoc.getDeliveries().size();
            if (n == 0) {
                link.text(" 本订单尚未发货。");
            } else {
                link.text(" 已有 " + n + " 张发货单。");
            }
        }

        return false;
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<SalesOrder> ref, IOptions options)
            throws ViewBuilderException, IOException {
        SalesOrder sdoc = ref.get();
        Integer id = sdoc.getId();
        if (id == null)
            return out;

        PathFieldMap itemIndexFields;
        {
            IType itemType = PotatoTypes.getInstance().forClass(SalesOrderItem.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            itemIndexFields = new PathFieldMap(itemFormDecl);
            try {
                itemIndexFields.parse("i*s", SalesOrderItemIndexVbo.FIELDS);
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        ItemsTable itab = new ItemsTable(out, "entries",//
                _webApp_ + "sentry/ID/?view:=form&order.id=" + id);
        itab.ajaxUrl = "../../sentry/data.json?doc=" + id;
        itab.buildHeader(itemIndexFields.values());

        SectionDiv section;
        {
            section = new SectionDiv(out, "s-delivery", "发货情况", IFontAwesomeCharAliases.FA_TRUCK);
            buildDeliveryList(ctx, section.contentDiv, ref, options);
        }
        return out;
    }

    protected void buildDeliveryList(IHttpViewContext ctx, IHtmlTag out, IUiRef<SalesOrder> ref, IOptions options)
            throws ViewBuilderException, IOException {
        DeliveryMapper deliveryMapper = ctx.query(DeliveryMapper.class);

        SalesOrder sdoc = ref.get();
        Map<Long, Double> dMap = new TreeMap<>();

        for (SalesOrderItem item : sdoc.getItems())
            dMap.put(item.getId(), item.getQuantity());

        HtmlUlTag ol = out.ul().id("deliveries");
        for (Delivery delivery : sdoc.getDeliveries()) {
            delivery = deliveryMapper.select(delivery.getId()); // Full reload.
            for (DeliveryItem di : delivery.getItems()) {
                SalesOrderItem si = di.getSalesOrderItem();
                long sid = si.getId();
                Double remaining = dMap.get(sid);
                if (remaining == null)
                    continue;
                dMap.put(sid, remaining - di.getQuantity());
            }

            HtmlLiTag li = ol.li();

            String href = _webApp_ + "dldoc/" + delivery.getId() + "/";
            li.a().class_("refid").href("_blank", href).iText(FA_INFO, "fa").text("送货单 - " + delivery.getId());
            li.text(": ");

            li.span().class_("date").verbatim(DateUtils.formatRange(delivery, "未发货", "未签收"));

            li.div().class_("small").verbatim(delivery.getText());
            HtmlDlTag dl = li.dl();
            dl.dt().text("客户:");
            {
                Organization org = delivery.getOrg();
                Person person = delivery.getPerson();
                if (org != null)
                    dl.dd().text(org.getFullName());
                if (person != null)
                    dl.dd().text(person.getFullName());
            }

            Contact shipDest = delivery.getShipDest();
            if (shipDest != null) {
                dl.dt().text("目的地:");
                dl.dd().text(shipDest);
            }
        } // for delivery

        double remainingTotal = 0;
        for (Double q : dMap.values())
            remainingTotal += q;

        out.hr();

        IHtmlTag h = out.table().class_("magic").tr();
        h.td().img().src(_webApp_ + "img1/girl2r.png").title("我是自动生成数据机器人Giri");
        h = h.td();

        double epsn = DoubleUtils.epsilon * sdoc.getItems().size();
        if (remainingTotal < epsn) {
            h.text("呼~~ 所有项目都已发货啦。");
        } else {
            h.h4().text("这些项目还没有发货，赶紧根据剩余的数量安排发货单吧！") //
                    .small().text("（如果您修改了订单项目，需要保存后才能看到更新的剩余数量。）");

            HtmlFormTag form = h.form().id("dldoc-creator").method("post").target("_blank")//
                    .action(_webApp_ + "dldoc/new/");
            form.input().type("hidden").name("m:").value(MethodNames.CREATE);
            form.input().type("hidden").name("-nav").value("reload");
            form.input().type("hidden").name("prompt.success").value("已生成一张送货单，请补充相关的信息。");
            form.input().type("hidden").name("subject").value("送货:" + sdoc.getSubject());
            form.input().type("hidden").name("salesOrder.id").value(sdoc.getId());
            form.input().type("hidden").name("salesOrder.subject").value(sdoc.getSubject());
            if (sdoc.getOrg() != null) {
                form.input().type("hidden").name("org.id").value(sdoc.getOrg().getId());
                form.input().type("hidden").name("org.label").value(sdoc.getOrg().getLabel());
            }
            if (sdoc.getPerson() != null) {
                form.input().type("hidden").name("person.id").value(sdoc.getPerson().getId());
                form.input().type("hidden").name("person.label").value(sdoc.getPerson().getLabel());
            }

            HtmlTableTag table = form.table()
                    .class_("table table-striped table-hover table-condensed table-responsive");
            HtmlTrTag htr = table.thead().tr();
            htr.th().text("订单项");
            htr.th().text("货物");
            htr.th().text("单位");
            htr.th().text("注释");
            htr.th().text("剩余数量");

            for (SalesOrderItem item : sdoc.getItems()) {
                Double remaining = dMap.get(item.getId());
                if (remaining == null)
                    remaining = item.getQuantity();
                if (remaining <= DoubleUtils.epsilon)
                    continue;
                HtmlTrTag tr = table.tr();
                tr.td().text(item.getId());

                String label;
                String uom;
                if (!Nullables.isEmpty(item.getAltLabel())) {
                    label = item.getAltLabel() + " - " + item.getAltSpec();
                    uom = "";
                } else {
                    Artifact art = item.getArtifact();
                    label = art.getLabel() + " - " + art.getSpec();
                    uom = art.getUom().getLabel();
                }
                tr.td().text(label);
                tr.td().text(uom);
                tr.td().text(item.getComment());

                String str = String.format("%.4f", remaining);
                if (str.endsWith(".0000"))
                    str = str.substring(0, str.length() - 5);
                HtmlTdTag td = tr.td();
                td.input().type("number").name("qty-" + item.getId()).value(str).style("width: 4em");
                td.input().type("hidden").name("price-" + item.getId()).value(item.getPrice());
            } // for item

            form.input().type("submit").value("生成送货单");
        }

    }
}
