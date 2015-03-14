package com.bee32.zebra.oa.site;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.i18n.dom1.IElement;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.PathArrivalEntry;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.htm.IPageLayoutGuider;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.htm.RespTemplate;
import com.tinylily.model.base.security.LoginContext;

public class OaSiteVbo
        extends RespTemplate<OaSite> {

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
    public void preview(IHttpViewContext ctx, IUiRef<OaSite> ref, IOptions options) {
        super.preview(ctx, ref, options);

        IPathArrival arrival = ctx.query(IPathArrival.class);
        PageLayout layout = solicitLayout(arrival);
        ctx.setAttribute(PageLayout.ATTRIBUTE_KEY, layout);
    }

    @Override
    public IHtmlTag buildHtmlView(IHttpViewContext ctx, IHtmlTag out, IUiRef<OaSite> ref, IOptions options)
            throws ViewBuilderException, IOException {
        OaSite site = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(site).getRemainingPath() == null;

        if (arrivedHere && enter(ctx))
            return null;

        LoginContext loginctx = LoginContext.fromSession(ctx.getSession());
        if (loginctx == null) {
            ctx.getResponse().sendRedirect(_webApp_ + "login/");
            return null;
        }

        HtmlHeadTag head = out.head().id("_head");
        respHead(ctx, head);

        HtmlBodyTag body = out.body();
        // HtmlImgTag bg = body.img().class_("background");
        // bg.src(_webApp_ + "chunk/pic/bg/poppy-orange-flower-bud.jpg");

        out = body;

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (!layout.hideFramework) {
            HtmlDivTag container = body.div().class_("container").style("width: 100%; padding: 0");
            HtmlDivTag containerRow = container.div().class_("container-row");
            HtmlDivTag menuCol = containerRow.div().id("zp-menu-col")//
                    .class_("col-xs-3 col-sm-2 col-lg-1");
            menuCol(ctx, menuCol, ref, arrival);

            HtmlDivTag body1 = containerRow.div().id(ID.body1)//
                    .class_("col-xs-9 col-sm-8 col-lg-10");
            containerRow.div().id(ID.right_col)//
                    .class_("col-xs-0 hidden-xs col-sm-2 col-lg-1 zu-info");

            out = body1;

            if (arrivedHere)
                defaultBody(body1, site);

            foot(body, site);
        }

        body.div().id(ID.extradata);

        HtmlDivTag scripts = body.div().id(ID.scripts);
        scripts.script().javascriptSrc(_webApp_ + "makeup.js");

        return out;
    }

    PageLayout solicitLayout(IPathArrival arrival) {
        PageLayout layout = new PageLayout();
        for (IPathArrival a : arrival.toList(false)) { // should be in reversed order.
            Object target = a.getTarget();
            if (target instanceof IPageLayoutGuider)
                ((IPageLayoutGuider) target).configure(layout);
        }
        return layout;
    }

    protected void menuCol(IHttpViewContext ctx, IHtmlTag out, IUiRef<OaSite> ref, IPathArrival arrival) {
        OaSite site = ref.get();
        LoginContext loginctx = LoginContext.fromSession(ctx.getSession());

        if (ref instanceof PathArrivalEntry) {
            IHtmlTag nav = out.nav().ol().class_("breadcrumb");
            List<IPathArrival> list = arrival.toList(true);
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

        HtmlDivTag logoDiv = out.div().id("zp-logo").text("SECCA");
        logoDiv.br();
        logoDiv.text("ERP 3.0alpha");

        HtmlDivTag welcomeDiv = out.div().id("zp-welcome");
        welcomeDiv.text("欢迎您，").br();
// welcomeDiv.text("海宁中鑫三元风机有限公司").br();
// welcomeDiv.text("的").br();
        HtmlSpanTag userSpan = welcomeDiv.span();
        userSpan.text(loginctx.user.getFullName());
        userSpan.title(loginctx.user.getGroupIds().toString());
        welcomeDiv.text("！");
        HtmlATag logout = welcomeDiv.a().id("zp-logout");
        logout.href(_webApp_ + "login/?logout=1");
        logout.text("[注销]");

        HtmlFormTag searchForm = out.form().id("zp-search");
        searchForm.text("搜索：");
        searchForm.input().id("q");

        out.hr();
        HtmlDivTag menuDiv = out.div().id("zp-menu");
        HtmlUlTag menuUl = menuDiv.ul().id(ID.menu_ul);
        mainMenu(menuUl, site);
    }

    protected void mainMenu(IHtmlTag out, OaSite site) {
        HtmlUlTag sub;
        HtmlLiTag item;

        sub = out.li().text("控制台").ul();
        // sub.li().a().text("日历").href(_webApp_.join("cal/").toString());
        sub.li().a().text("日记").href(_webApp_.join("diary/").toString());
        // sub.li().a().text("设置").href(_webApp_.join("setting/").toString());

        sub = out.li().text("知识库").ul();
        sub.li().a().text("企、事业").href(_webApp_.join("org/").toString());
        sub.li().a().text("联系人").href(_webApp_.join("person/").toString());
        sub.li().a().text("文件").href(_webApp_.join("file/").toString());

        item = out.li().text("库存");
        item.a().text("[初始化]").href(_webApp_.join("stinit/").toString()).class_("small").style("color: gray");

        sub = item.ul();
        sub.li().a().text("区域").href(_webApp_.join("place/").toString());
        sub.li().a().text("产品/物料").href(_webApp_.join("art/").toString());
        sub.li().a().text("作业").href(_webApp_.join("stdoc/").toString());
        sub.li().a().text("盘点/报损").href(_webApp_.join("stdoc/new").toString());

        sub = out.li().text("销售").ul();
        sub.li().a().text("项目/机会").href(_webApp_.join("topic/").toString());
        sub.li().a().text("订单").href(_webApp_.join("sdoc/").toString());
        sub.li().a().text("送货").href(_webApp_.join("dldoc/").toString());

        sub = out.li().text("生产过程").ul();
        sub.li().a().text("装配图").href(_webApp_.join("bom/").toString());
        sub.li().a().text("工艺路线").href(_webApp_.join("fabproc/").toString());
        sub.li().a().text("排程").href(_webApp_.join("sch/").toString());
        sub.li().a().text("作业").href(_webApp_.join("job/").toString());
        sub.li().a().text("质量控制").href(_webApp_.join("qc/").toString());

        item = out.li().text("财务");
        item.a().text("[初始化]").href(_webApp_.join("acinit/").toString()).class_("small").style("color: gray");
        sub = item.ul();
        sub.li().a().text("填表").href(_webApp_.join("acdoc/?phase=1").toString());
        sub.li().a().text("流水帐").href(_webApp_.join("acdoc/?phase=2").toString());
        sub.li().a().text("工资").href(_webApp_.join("salary/").toString());
        sub.li().a().text("分析").href(_webApp_.join("acstat/").toString());

        sub = out.li().text("系统").ul();
        sub.li().a().text("帐户").href(_webApp_.join("user/").toString());
    }

    protected void defaultBody(IHtmlTag out, OaSite site) {
        HtmlH1Tag h1 = out.h1().text("List Of Projects");
        h1.a().style("cursor: pointer").onclick("reloadSite()").text("[Reload]");
    }

    protected void foot(IHtmlTag out, OaSite site) {
        HtmlDivTag foot = out.div().id("zp-foot");
        // ClassDoc doc = Xjdocs.getDefaultProvider().getClassDoc(site.getClass());

    }

}
