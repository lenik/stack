package com.bee32.zebra.oa.site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.i18n.dom1.IElement;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.PathArrivalEntry;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.security.LoginData;

import com.bee32.zebra.tk.hbin.ShareBar_htm1;
import com.bee32.zebra.tk.htm.IPageLayoutGuider;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.htm.RespTemplate;

public class OaSite_htm
        extends RespTemplate<OaSite>
        implements IFontAwesomeCharAliases {

    public OaSite_htm() {
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
    public void precompile(IHtmlViewContext ctx, IUiRef<OaSite> ref) {
        super.precompile(ctx, ref);

        IPathArrival arrival = ctx.query(IPathArrival.class);
        PageLayout layout = solicitLayout(arrival);
        ctx.setAttribute(PageLayout.ATTRIBUTE_KEY, layout);
    }

    @Override
    public IHtmlOut buildHtmlViewStart(IHtmlViewContext ctx, IHtmlOut out, IUiRef<OaSite> ref)
            throws ViewBuilderException, IOException {
        OaSite site = ref.get();
        IPathArrival arrival = ctx.query(IPathArrival.class);
        boolean arrivedHere = arrival.getPrevious(site).getRemainingPath() == null;

        if (arrivedHere && fn.redirect.addSlash(ctx))
            return null;

        LoginData loginData = LoginData.fromSession(ctx.getSession());
        if (loginData == null) {
            ctx.getResponse().sendRedirect(_webApp_ + "login/");
            return ctx.stop();
        }

        HtmlHead head = out.head().id("_head");
        respHead(ctx, head);

        HtmlBody body = out.body();
        // HtmlImg bg = body.img().class_("background");
        // bg.src(_webApp_ + "chunk/pic/bg/poppy-orange-flower-bud.jpg");

        out = body;

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (layout.isShowFrame()) {
            HtmlDiv container = body.div().class_("container").style("width: 100%; padding: 0");
            HtmlDiv containerRow = container.div().class_("container-row");
            HtmlDiv menuCol = containerRow.div().id("zp-menu-col")//
                    .class_("col-xs-3 col-sm-2 col-lg-1");
            menuCol(ctx, menuCol, ref, arrival);

            HtmlDiv body1 = containerRow.div().id(ID.body0)//
                    .class_("col-xs-9 col-sm-10 col-lg-11");

            out = body1;

            if (arrivedHere)
                defaultBody(body1, site);
        }

        ctx.setVariable(VAR.extraScripts, new ArrayList<String>());
        return out;
    }

    @Override
    public void buildHtmlViewEnd(IHtmlViewContext ctx, IHtmlOut out, IHtmlOut body, IUiRef<OaSite> ref)
            throws ViewBuilderException, IOException {
        OaSite site = ref.get();

        PageLayout layout = ctx.getAttribute(PageLayout.ATTRIBUTE_KEY);
        if (layout.isShowFrame())
            foot(body, site);

        // body.div().id(ID.extradata);

        HtmlDiv scripts = body.div().id(ID.scripts);
        scripts.script().javascriptSrc(_webApp_ + "js1/util.0.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/util.datatables.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/util.flot.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/util.jquery.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/util.window.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/widget.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/makeup.js");
        scripts.script().javascriptSrc(_webApp_ + "js1/index.charts.js");

        List<String> extraScripts = ctx.getVariable(VAR.extraScripts);
        for (String s : extraScripts)
            scripts.script().javascriptSrc(s);
    }

    PageLayout solicitLayout(IPathArrival arrival) {
        PageLayout layout = new PageLayout();
        for (IPathArrival a : arrival.toList()) { // should be in reversed order.
            Object target = a.getTarget();
            if (target instanceof IPageLayoutGuider)
                ((IPageLayoutGuider) target).configure(layout);
        }
        return layout;
    }

    protected void menuCol(IHtmlViewContext ctx, IHtmlOut out, IUiRef<OaSite> ref, IPathArrival arrival) {
        OaSite site = ref.get();
        LoginData loginctx = LoginData.fromSession(ctx.getSession());

        if (ref instanceof PathArrivalEntry) {
            IHtmlOut nav = out.nav().ol().class_("breadcrumb");
            List<IPathArrival> list = arrival.toList().mergeTransients();
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
                HtmlLi li = nav.li();
                if (last)
                    li.class_("active").text(label);
                else
                    li.a().href(href).text(label);
            }
        }

        HtmlDiv logoDiv = out.div().id("zp-logo").text("SECCA");
        logoDiv.br();
        logoDiv.text("ERP 3.0alpha");

        HtmlDiv welcomeDiv = out.div().id("zp-welcome");
        welcomeDiv.text("????????????").br();
        // welcomeDiv.text("????????????????????????????????????").br();
        // welcomeDiv.text("???").br();
        HtmlSpan userSpan = welcomeDiv.span();
        userSpan.title(loginctx.user.getGroupIds().toString());
        userSpan.text(loginctx.user.getFullName());
        welcomeDiv.text("???");
        HtmlA logout = welcomeDiv.a().id("zp-logout");
        logout.href(_webApp_ + "login/?logout=1");
        logout.text("[??????]");

        HtmlForm searchForm = out.form().id("zp-search");
        searchForm.text("?????????");
        searchForm.input().id("q");

        out.hr();
        HtmlDiv menuDiv = out.div().id("zp-menu");
        HtmlUl menuUl = menuDiv.ul().id(ID.menu_ul);
        mainMenu(menuUl, site);
    }

    protected void mainMenu(IHtmlOut out, OaSite site) {
        HtmlUl sub;
        HtmlLi li;
        HtmlLi lili;

        sub = out.li().text("??????").ul();
        sub.li().a().href(_webApp_.join("console/")).text("?????????");
        li = sub.li();
        li.a().href(_webApp_.join("diary/")).text("??????");
        li.text(" / ");
        li.a().href(_webApp_.join("calendar/")).text("??????");
        // sub.li().a().href(_webApp_.join("post/")).text("??????");

        sub = out.li().text("?????????").ul();
        sub.li().a().href(_webApp_.join("org/")).text("????????????");
        sub.li().a().href(_webApp_.join("person/")).text("?????????");
        sub.li().a().href(_webApp_.join("file/")).text("??????");

        li = out.li().text("??????");
        li.a().href(_webApp_.join("stinit/")).class_("small").style("color: gray").text("[?????????]");

        sub = li.ul();
        sub.li().a().href(_webApp_.join("place/")).text("??????");
        sub.li().a().href(_webApp_.join("art/")).text("??????/??????");
        sub.li().a().href(_webApp_.join("stdoc/")).text("??????");
        sub.li().a().href(_webApp_.join("stdoc/new")).text("??????/??????");

        sub = out.li().text("??????").ul();
        sub.li().a().href(_webApp_.join("topic/")).text("??????/??????");
        sub.li().a().href(_webApp_.join("sdoc/")).text("??????");
        sub.li().a().href(_webApp_.join("dldoc/")).text("??????");

        sub = out.li().text("????????????").ul();
        sub.li().a().href(_webApp_.join("bom/")).text("?????????");
        sub.li().a().href(_webApp_.join("fabproc/")).text("????????????");
        sub.li().a().href(_webApp_.join("sch/")).text("??????");
        sub.li().a().href(_webApp_.join("job/")).text("??????");
        sub.li().a().href(_webApp_.join("qc/")).text("????????????");

        li = out.li().text("??????");
        li.a().href(_webApp_.join("acinit/")).class_("small").style("color: gray").text("[?????????]");
        sub = li.ul();

        lili = sub.li();
        lili.a().href(_webApp_.join("acdoc/?phase=1")).text("??????");
        lili.text(" / ");
        lili.a().href(_webApp_.join("acdoc/new?phase=1")).text("??????");

        sub.li().a().href(_webApp_.join("acdoc/?phase=2")).text("?????????");
        sub.li().a().href(_webApp_.join("salary/")).text("??????");
        sub.li().a().href(_webApp_.join("acstat/")).text("??????");

        LoginData login = LoginData.fromSession();
        if (login.user.isAdmin()) {
            sub = out.li().text("??????").ul();
            sub.li().a().href(_webApp_.join("user/")).text("??????");
            // sub.li().a().href(_webApp_.join("setting/")).text("??????");
        }
    }

    protected void defaultBody(IHtmlOut out, OaSite site) {
        out.link().css("home.css");
        out.script().javascriptSrc("home.js");

        HtmlDiv div = out.div().align("center");
        div.img().id("welcome").src(_chunk_ + "pic/sym/welcome/colorful1.png").width("75%").style("display: none;");

    }

    protected void foot(IHtmlOut out, OaSite site) {
        out = out.div().id("zp-foot");
        // ClassDoc doc = Xjdocs.getDefaultProvider().getClassDoc(site.getClass());

        new ShareBar_htm1().build(out);
        out.hr().style("clear: both; margin: .5em 0");

        HtmlDiv navfoot = out.div().class_("zu-footnav");
        HtmlTable tab = navfoot.table().align("center");
        HtmlTr row;
        HtmlTd cell;

        row = tab.tr();
        cell = row.td().align("right");
        cell.img().title("Secca Zebra").src(_webApp_ + "seccazebra.png").width("80");
        cell.br();
        cell.div().id("zp-qrchere").title("??????????????????");

        cell = row.td();
        cell.b().text("??????");
        HtmlUl ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "console").iText(FA_TACHOMETER, "fa").text("?????????");
        ul.li().a().href("_blank", _webApp_ + "person/").iText(FA_MALE, "fa").text("?????????");
        ul.li().a().href("_blank", _webApp_ + "file/").iText(FA_FILES_O, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "art/").iText(FA_SITEMAP, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "sales").iText(FA_TABLE, "fa").text("?????????");
        ul.li().a().href("_blank", _webApp_ + "man").iText(FA_RANDOM, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "acc").iText(FA_USD, "fa").text("????????????");

        cell = row.td();
        cell.b().text("??????");
        ul = cell.ul();
        // http://www.online-calculator.com/simple-full-screen-calculator/
        ul.li().a().href("_blank", "http://web2.0calc.com/").iText(FA_CALCULATOR, "fa").text("?????????");
        ul.li().a().href("_blank", "http://www.laohuangli.net/").iText(FA_CALENDAR, "fa").text("?????????");
        ul.li().a().href("_blank", "http://www.boc.cn/sourcedb/whpj/").iText(FA_EXCHANGE, "fa").text("????????????");
        ul.li().a().href("_blank", "http://www.qg68.cn/news/").iText(FA_BOOK, "fa").text("????????????");

        cell = row.td();
        cell.b().text("??????");
        ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "service/qq").iText(FA_QQ, "fa").text("??????");
        ul.li().a().href("_blank", _webApp_ + "service/purchase").iText(FA_SHOPPING_CART, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "service/promote").iText(FA_VOLUME_UP, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "service/solution").iText(FA_CAR, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "service/pad").iText(FA_ANDROID, "fa").text("??????/??????");
        ul.li().a().href("_blank", _webApp_ + "service/qrcode").iText(FA_QRCODE, "fa").text("?????????");
        ul.li().a().href("_blank", _webApp_ + "service/rfid").iText(FA_WIFI, "fa").text("RFID");

        cell = row.td();
        cell.b().text("??????");
        ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "case/zjhf").iText(FA_STAR_O, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "case/blgd").iText(FA_STAR_O, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "case/hxgg").iText(FA_STAR_O, "fa").text("???????????????");
        ul.li().a().href("_blank", _webApp_ + "case/sdty").iText(FA_STAR_O, "fa").text("???????????????");
        ul.li().a().href("_blank", _webApp_ + "case/hmzy").iText(FA_STAR_O, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "case/dehg").iText(FA_STAR_O, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "case/yxwdz").iText(FA_STAR_O, "fa").text("???????????????");

        cell = row.td();
        cell.b().text("??????");
        ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "util/issue").iText(FA_BUG, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "util/dashboard").iText(FA_BUILDING_O, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "util/backup").iText(FA_DATABASE, "fa").text("????????????");
        ul.li().a().href("_blank", _webApp_ + "util/restore").iText(FA_AMBULANCE, "fa").text("????????????");

        out.hr().style("margin: .5em 0");
        HtmlDiv div = out.div().align("center").class_("zu-footcopy");
        div.text("????????????????????? (C) ?????????????????????????????????????????? 2010-2015 ");
        div.a().href("_blank", "about").text("(??????/??????)");
    }

}
