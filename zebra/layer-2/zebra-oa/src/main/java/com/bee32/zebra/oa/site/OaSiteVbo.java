package com.bee32.zebra.oa.site;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.bodz.bas.c.string.StringQuote;
import net.bodz.bas.html.artifact.IArtifactConsts;
import net.bodz.bas.html.artifact.IArtifactDependency;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlHeadData;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.i18n.dom1.IElement;
import net.bodz.bas.potato.ref.UiHelper;
import net.bodz.bas.potato.ref.UiPropertyRefMap;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.PathArrivalEntry;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;

public class OaSiteVbo
        extends AbstractHtmlViewBuilder<OaSite>
        implements IZebraSiteAnchors, IZebraSiteLayout, IArtifactConsts {

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

        IHtmlHeadData metaData = ctx.getHeadData();
        metaData.setMeta(IHtmlHeadData.META_AUTHOR, "谢继雷 (Xiè Jìléi)");
        metaData.setMeta(IHtmlHeadData.META_VIEWPORT, "width=device-width, initial-scale=1");

        /** See: BasicSiteArtifacts */
        // metaData.addDependency("datatables.bootstrap.js", SCRIPT);
        // metaData.addDependency("datatables.responsive.js", SCRIPT);
        metaData.addDependency("datatables.colVis.js", SCRIPT);
        metaData.addDependency("datatables.tableTools.js", SCRIPT);
        metaData.addDependency("font-awesome", STYLESHEET).setPriority(IArtifactDependency.LOW);
        metaData.addDependency("jquery-min", SCRIPT).setPriority(IArtifactDependency.HIGH);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<OaSite> ref, IOptions options)
            throws ViewBuilderException, IOException {
        if (enter(ctx))
            return null;

        OaSite site = ref.get();
        IHtmlTag out = ctx.getOut();

        HttpSession session = ctx.getSession();
        // Preferences pref = Preferences.fromSession(session);

        UiPropertyRefMap propMap = UiHelper.explode(ref);

        IPathArrival arrival = (IPathArrival) ctx.getRequest().getAttribute(IPathArrival.class.getName());
        boolean frameOnly = arrival.getPrevious(site).getRemainingPath() != null;
        HtmlHeadTag head = out.head();
        {
            writeHeadMetas(ctx, head);
            writeHeadImports(ctx, head);

            // stylesheets
            head.link().css(_webApp_ + "site.css");
            // head.link().css(_webApp_ + "s-yellow.css");
            // head.link().css(_webApp_ + "theme-" + pref.getTheme() + ".css").id("zp-themeLink");
            head.link().css(_webApp_ + "print.css").media("print");

            // scripts
            head.script().javascript("" + //
                    "var _webApp_ = " + StringQuote.qq(_webApp_) + ";\n" + //
                    "var _js_ = " + StringQuote.qq(_js_) + ";\n" + //
                    "");

            head.script().javascriptSrc(_webApp_ + "site.js");
        }

        HtmlBodyTag body = out.body();

        HtmlDivTag containerDiv = body.div().class_("container").style("width: 100%; padding: 0");
        HtmlDivTag rowDiv = containerDiv.div().class_("container-row");

        HtmlDivTag menuCol = rowDiv.div().id("zp-menu-col").class_("col-sm-2");
        {
            if (ref instanceof PathArrivalEntry) {
                IHtmlTag nav = menuCol.nav().ol().class_("breadcrumb");
                List<IPathArrival> list = arrival.toList();
                for (int i = 0; i < list.size(); i++) {
                    IPathArrival a = list.get(i);
                    Object target = a.getTarget();

                    String label;
                    if (target instanceof IElement) {
                        iString _label = ((IElement) target).getLabel();
                        if (_label == null)
                            label = "i18n-null";
                        else
                            label = _label.toString();
                    } else
                        label = target.toString();

                    String href = _webApp_.join(a.getConsumedFullPath() + "/").toString();

                    boolean last = i == list.size() - 1;
                    HtmlLiTag li = nav.li();
                    if (last)
                        li.class_("active").text(label);
                    else
                        li.a().href(href).text(label);
                }
            }

            HtmlDivTag logoDiv = menuCol.div().id("zp-logo").text("SECCA");
            logoDiv.br();
            logoDiv.text("ERP 3.0alpha");

            HtmlDivTag welcomeDiv = menuCol.div().id("zp-welcome");
            welcomeDiv.text("欢迎您，").br();
            welcomeDiv.text("海宁皮包有限公司").br();
            welcomeDiv.text("的").br();
            welcomeDiv.text("张三 销售员！");

            HtmlSpanTag logout = welcomeDiv.span().id("zp-logout");
            logout.text("[注销]");

            HtmlFormTag searchForm = menuCol.form().id("zp-search");
            searchForm.text("搜索：");
            searchForm.input().id("q");

            menuCol.hr();
            HtmlDivTag menuDiv = menuCol.div().id("zp-menu");
            HtmlUlTag menuUl = menuDiv.ul().id(ID.menu_ul);
            mkMenu(menuUl);
        }

        HtmlDivTag body1 = rowDiv.div().id(ID.body1).class_("col-xs-12 col-sm-10");
        if (!frameOnly)
            indexBody(body1, site);

        HtmlDivTag foot = body.div().class_("zu-foot");
        {
            ClassDoc doc = Xjdocs.getDefaultProvider().getClassDoc(site.getClass());
            if (doc != null)
                foot.text(doc.getTag("copyright"));
        }

        body.div().id(ID.extradata);

        ctx.setOut(body1);
        return ctx;
    }

    protected void indexBody(IHtmlTag out, OaSite site) {
        HtmlH1Tag h1 = out.h1().text("List Of Projects");
        h1.a().style("cursor: pointer").onclick("reloadSite()").text("[Reload]");
    }

    protected void mkMenu(IHtmlTag parent) {
        HtmlUlTag sub;
        HtmlLiTag item;
        sub = parent.li().text("知识库").ul();
        sub.li().a().text("企事、业").href(_webApp_.join("org/").toString());
        sub.li().a().text("联系人").href(_webApp_.join("person/").toString());
        sub.li().a().text("文件").href(_webApp_.join("file/").toString());

        item = parent.li().text("库存");
        item.a().text("[初始化]").href(_webApp_.join("stinit/").toString()).class_("small").style("color: gray");

        sub = item.ul();
        sub.li().a().text("区域").href(_webApp_.join("place/").toString());
        sub.li().a().text("产品/物料").href(_webApp_.join("art/").toString());
        sub.li().a().text("作业").href(_webApp_.join("stdoc/").toString());
        sub.li().a().text("盘点/报损").href(_webApp_.join("stdoc/new").toString());

        sub = parent.li().text("销售").ul();
        sub.li().a().text("项目/机会").href(_webApp_.join("topic/").toString());
        sub.li().a().text("订单").href(_webApp_.join("sdoc/").toString());
        sub.li().a().text("送货").href(_webApp_.join("dldoc/").toString());

        sub = parent.li().text("生产过程").ul();
        sub.li().a().text("装配图").href(_webApp_.join("bom/").toString());
        sub.li().a().text("工艺路线").href(_webApp_.join("fabproc/").toString());
        sub.li().a().text("排程").href(_webApp_.join("sch/").toString());
        sub.li().a().text("作业").href(_webApp_.join("job/").toString());
        sub.li().a().text("质量控制").href(_webApp_.join("qc/").toString());

        item = parent.li().text("财务");
        item.a().text("[初始化]").href(_webApp_.join("acinit/").toString()).class_("small").style("color: gray");
        sub = item.ul();
        sub.li().a().text("填表").href(_webApp_.join("acdoc/?phase=1").toString());
        sub.li().a().text("流水帐").href(_webApp_.join("acdoc/?phase=2").toString());
        sub.li().a().text("工资").href(_webApp_.join("salary/").toString());
        sub.li().a().text("分析").href(_webApp_.join("acstat/").toString());

        sub = parent.li().text("系统").ul();
        sub.li().a().text("帐户").href(_webApp_.join("user/").toString());
    }

}
