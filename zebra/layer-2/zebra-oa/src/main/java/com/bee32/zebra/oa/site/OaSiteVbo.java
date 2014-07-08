package com.bee32.zebra.oa.site;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import net.bodz.bas.c.string.StringQuote;
import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlMetaData;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.artifact.IArtifactDependency;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.i18n.dom1.IElement;
import net.bodz.bas.potato.ref.UiPropertyRefMap;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.PathArrivalEntry;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.ClassDocLoader;
import net.bodz.mda.xjdoc.model.ClassDoc;

public class OaSiteVbo
        extends AbstractHtmlViewBuilder<OaSite>
        implements IOaSiteAnchors, IOaSiteLayout {

    public OaSiteVbo() {
        super(OaSite.class);
    }

    @Override
    public boolean isOrigin(OaSite value) {
        return true;
    }

    @Override
    public boolean isFrame() {
        return true;
    }

    @Override
    public void preview(IHtmlViewContext ctx, IUiRef<OaSite> ref, IOptions options) {
        super.preview(ctx, ref, options);

        IHtmlMetaData metaData = ctx.getMetaData();
        metaData.setMeta(IHtmlMetaData.META_AUTHOR, "谢继雷 (Xiè Jìléi)");
        metaData.setMeta(IHtmlMetaData.META_VIEWPORT, "width=device-width, initial-scale=1");

        metaData.addDependency("jquery-min", IArtifactDependency.SCRIPT);
        metaData.addDependency("font-awesome", IArtifactDependency.STYLESHEET).setPriority(IArtifactDependency.LOW);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<OaSite> ref, IOptions options)
            throws ViewBuilderException, IOException {
        if (enter(ctx))
            return null;

        IHtmlTag out = ctx.getOut();
        OaSite site = ref.get();

        HttpSession session = ctx.getSession();
        // Preferences pref = Preferences.fromSession(session);

        UiPropertyRefMap propMap = explode(ref);

        IPathArrival arrival = (IPathArrival) ctx.getRequest().getAttribute(IPathArrival.class.getName());
        boolean frameOnly = arrival.getPrevious(site).getRemainingPath() != null;
        HtmlHeadTag head = out.head();
        {
            writeHeadMetas(ctx, head);
            writeHeadImports(ctx, head);

            // stylesheets
            head.link().css(_webApp_ + "site.css");
            // head.link().css(_webApp_ + "theme-" + pref.getTheme() + ".css").id("themeLink");

            // scripts
            head.script().javascript("var _webApp_ = " + StringQuote.qq(_webApp_));
            head.script().javascriptSrc(_webApp_ + "site.js");
        }

        out = out.body();

        HtmlTableTag layoutTable = out.table().class_("layout");

        HtmlTrTag topTr = layoutTable.tr().valign("top").style("height: 0");
        HtmlTdTag leftBar = topTr.td().id("left").rowspan("2");
        {
            HtmlDivTag logoDiv = leftBar.div().id("logo").text("SECCA");
            logoDiv.br();
            logoDiv.text("ERP");

            HtmlDivTag welcomeDiv = leftBar.div().id("welcome");
            welcomeDiv.text("欢迎您，").br();
            welcomeDiv.text("海宁皮包有限公司").br();
            welcomeDiv.text("的").br();
            welcomeDiv.text("张三 销售员！");

            HtmlSpanTag logout = welcomeDiv.span().id("logout");
            logout.text("[注销]");

            HtmlFormTag searchForm = leftBar.form().id("search");
            searchForm.text("搜索：");
            searchForm.input().id("q");

            HtmlDivTag menuDiv = leftBar.div().id("menu");
            ctx.setAnchor(UL_menu, menuDiv.ul());
        }

        HtmlTdTag topBar = topTr.td().id("topbar").class_("info");
        {
            HtmlDivTag topTop = topBar.div().id("topTop");
            ctx.setAnchor(E_topTop, topTop);

            HtmlTableTag topTable = topBar.table().class_("layout").width("100%");
            {
                HtmlTrTag tr1 = topTable.tr().valign("top");
                HtmlTdTag statTd = tr1.td().id("stat").width("50%");

                ctx.setAnchor(E_stats, statTd);

                HtmlTdTag relLinksTd = tr1.td().id("links").rowspan("2").width("50%");
                relLinksTd.span().text("您可能需要进行下面的操作:");
                ctx.setAnchor(UL_relLinks, relLinksTd.ul());

                HtmlTrTag tr2 = topTable.tr();
                HtmlTdTag cmdsTd = tr2.td().id("cmds");
                ctx.setAnchor(E_cmds0, cmdsTd.div().id("cmds.0"));
                ctx.setAnchor(E_cmds1, cmdsTd.div().id("cmds.1"));
            }
        }

        HtmlTdTag rightTd = topTr.td().id("right").class_("info").rowspan("2");
        {
            HtmlDivTag previewDiv = rightTd.div().id("preview").align("center");
            previewDiv.img().src("pic.png");

            HtmlDivTag infosel = rightTd.div().id("infosel");

            HtmlTableTag _table1 = infosel.table().width("100%").class_("layout");
            HtmlTrTag _tr1 = _table1.tr();
            HtmlTdTag _td1 = _tr1.td();
            _td1.h2().text("选中的信息");
            HtmlTdTag _td2 = _tr1.td().align("right").style("width: 3em");
            _td2.a().href("").text("[编辑]");

            ctx.setAnchor(E_infosel, infosel);

            HtmlDivTag refdocsDiv = rightTd.div().id("infoman");
            refdocsDiv.h2().text("管理文献");
            ctx.setAnchor(UL_refdocs, refdocsDiv.ul());
        }

        HtmlTdTag contentTd = layoutTable.tr().valign("top").td().id("content");
        out = contentTd;

        if (ref instanceof PathArrivalEntry) {
            IHtmlTag nav = out.nav().ul();
            for (IPathArrival a : arrival.toList()) {
                Object target = a.getTarget();

                String label;
                if (target instanceof IElement)
                    label = ((IElement) target).getLabel().toString();
                else
                    label = target.toString();

                String href = _webApp_.join(a.getConsumedFullPath() + "/").toString();
                nav.li().a().href(href).text(label);
            }
        }

        HtmlDivTag mainDiv = out.div().id("main");
        if (!frameOnly)
            indexBody(mainDiv, site);

        ClassDoc classDoc = ClassDocLoader.load(site.getClass());

        HtmlDivTag foot = out.div().class_("foot");
        {
            foot.text(classDoc.getTag("copyright"));
        }

        ctx.setOut(mainDiv);
        return ctx;
    }

    protected void indexBody(IHtmlTag out, OaSite site) {
        HtmlH1Tag h1 = out.h1().text("List Of Projects");
        h1.a().style("cursor: pointer").onclick("reloadSite()").text("[Reload]");
    }

}
