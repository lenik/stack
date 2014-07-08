package com.bee32.zebra.erp.site;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlPTag;
import net.bodz.bas.html.dom.tag.HtmlTableTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.dom.tag.HtmlUlTag;
import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.ClassDocLoader;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.bee32.zebra.erp.stock.Artifact;
import com.bee32.zebra.oa.site.OaSite;
import com.bee32.zebra.oa.site.OaSiteVbo;

public class ErpSiteVbo
        extends OaSiteVbo {

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<OaSite> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ctx = super.buildHtmlView(ctx, ref, options);

        IHtmlTag topTop = ctx.getAnchor(E_topTop);
        IHtmlTag stats = ctx.getAnchor(E_stats);
        IHtmlTag cmds0 = ctx.getAnchor(E_cmds0);
        IHtmlTag cmds1 = ctx.getAnchor(E_cmds1);
        IHtmlTag refLinks = ctx.getAnchor(UL_relLinks);
        IHtmlTag infosel = ctx.getAnchor(E_infosel);
        IHtmlTag refDocs = ctx.getAnchor(UL_refdocs);

        ClassDoc classDoc = ClassDocLoader.load(Artifact.class);

        topTop.h1().text(classDoc.getTag("label"));
        HtmlPTag subTitle = topTop.p().class_("sub");
        subTitle.verbatim(classDoc.getText().getHeadPar());

        HtmlUlTag statsUl = stats.ul();
        statsUl.li().text("总计 3155 种");
        statsUl.li().text("有效 3014 种");
        statsUl.li().text("在用 541 种");

        cmds0.a().href("").text("新建");
        cmds0.a().href("").text("导出");
        cmds0.a().href("").text("打印");
        cmds1.a().href("").text("打印条码");

        List<String> rels = classDoc.getTag("rel", List.class);
        for (String rel : rels) {
            int colon = rel.indexOf(':');
            IAnchor href = _webApp_.join(rel.substring(0, colon).trim());
            String text = rel.substring(colon + 1).trim();
            refLinks.li().a().href(href.toString()).text(text);
        }

        HtmlTableTag infotab = infosel.table().width("100%");
        {
            HtmlTrTag tr;
            infotab.tr().td().class_("group").colspan("2").h3().text("基本信息");
            tr = infotab.tr();
            tr.td().class_("label").text("名称：");
            tr.td().text("茶杯");
            tr = infotab.tr();
            tr.td().class_("label").text("规格型号：");
            tr.td().text("S41-431");
            tr = infotab.tr();
            tr.td().class_("label").text("SKU：");
            tr.td().text("CUP4321");
            tr = infotab.tr();
            tr.td().class_("label").text("条形码：");
            tr.td().text("31746931");
            infotab.tr().td().class_("group").colspan("2").h3().text("运输");
            tr = infotab.tr();
            tr.td().class_("label").text("计量单位：");
            tr.td().text("个");
            tr = infotab.tr();
            tr.td().class_("label").text("毛重：");
            tr.td().text("120 g");
            tr = infotab.tr();
            tr.td().class_("label").text("净重：");
            tr.td().text("150 g");
            tr = infotab.tr();
            tr.td().class_("label").text("包装尺寸：");
            tr.td().text("310 x 540 x 210 mm");
            infotab.tr().td().class_("group").colspan("2").h3().text("自定义属性");
            tr = infotab.tr();
            tr.td().class_("label").text("色号：");
            tr.td().text("4312");
            tr = infotab.tr();
            tr.td().class_("label").text("桶号：");
            tr.td().text("54");
        }

        List<String> seeList = classDoc.getTag("see", List.class);
        for (String see : seeList)
            refDocs.li().verbatim(see);

        return ctx;
    }

    @Override
    protected void indexBody(IHtmlTag out, OaSite site) {
        out.h1().text("Test");

        IVirtualHost vhost = CurrentVirtualHost.getVirtualHost();
        if (vhost == null)
            out.text("No vhost.");
        else
            out.text(vhost.getName());
    }

}
