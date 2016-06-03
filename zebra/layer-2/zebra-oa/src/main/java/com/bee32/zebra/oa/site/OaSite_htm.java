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
        welcomeDiv.text("欢迎您，").br();
        // welcomeDiv.text("海宁中鑫三元风机有限公司").br();
        // welcomeDiv.text("的").br();
        HtmlSpan userSpan = welcomeDiv.span();
        userSpan.title(loginctx.user.getGroupIds().toString());
        userSpan.text(loginctx.user.getFullName());
        welcomeDiv.text("！");
        HtmlA logout = welcomeDiv.a().id("zp-logout");
        logout.href(_webApp_ + "login/?logout=1");
        logout.text("[注销]");

        HtmlForm searchForm = out.form().id("zp-search");
        searchForm.text("搜索：");
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

        sub = out.li().text("开始").ul();
        sub.li().a().href(_webApp_.join("console/")).text("控制台");
        li = sub.li();
        li.a().href(_webApp_.join("diary/")).text("日记");
        li.text(" / ");
        li.a().href(_webApp_.join("calendar/")).text("日历");
        // sub.li().a().href(_webApp_.join("post/")).text("论坛");

        sub = out.li().text("知识库").ul();
        sub.li().a().href(_webApp_.join("org/")).text("企、事业");
        sub.li().a().href(_webApp_.join("person/")).text("联系人");
        sub.li().a().href(_webApp_.join("file/")).text("文件");

        li = out.li().text("库存");
        li.a().href(_webApp_.join("stinit/")).class_("small").style("color: gray").text("[初始化]");

        sub = li.ul();
        sub.li().a().href(_webApp_.join("place/")).text("区域");
        sub.li().a().href(_webApp_.join("art/")).text("产品/物料");
        sub.li().a().href(_webApp_.join("stdoc/")).text("作业");
        sub.li().a().href(_webApp_.join("stdoc/new")).text("盘点/报损");

        sub = out.li().text("销售").ul();
        sub.li().a().href(_webApp_.join("topic/")).text("项目/机会");
        sub.li().a().href(_webApp_.join("sdoc/")).text("订单");
        sub.li().a().href(_webApp_.join("dldoc/")).text("送货");

        sub = out.li().text("生产过程").ul();
        sub.li().a().href(_webApp_.join("bom/")).text("装配图");
        sub.li().a().href(_webApp_.join("fabproc/")).text("工艺路线");
        sub.li().a().href(_webApp_.join("sch/")).text("排程");
        sub.li().a().href(_webApp_.join("job/")).text("作业");
        sub.li().a().href(_webApp_.join("qc/")).text("质量控制");

        li = out.li().text("财务");
        li.a().href(_webApp_.join("acinit/")).class_("small").style("color: gray").text("[初始化]");
        sub = li.ul();

        lili = sub.li();
        lili.a().href(_webApp_.join("acdoc/?phase=1")).text("单证");
        lili.text(" / ");
        lili.a().href(_webApp_.join("acdoc/new?phase=1")).text("填表");

        sub.li().a().href(_webApp_.join("acdoc/?phase=2")).text("流水帐");
        sub.li().a().href(_webApp_.join("salary/")).text("工资");
        sub.li().a().href(_webApp_.join("acstat/")).text("分析");

        LoginData login = LoginData.fromSession();
        if (login.user.isAdmin()) {
            sub = out.li().text("系统").ul();
            sub.li().a().href(_webApp_.join("user/")).text("帐户");
            // sub.li().a().href(_webApp_.join("setting/")).text("设置");
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
        cell.div().id("zp-qrchere").title("扫一扫当前页");

        cell = row.td();
        cell.b().text("常用");
        HtmlUl ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "console").iText(FA_TACHOMETER, "fa").text("控制台");
        ul.li().a().href("_blank", _webApp_ + "person/").iText(FA_MALE, "fa").text("联系人");
        ul.li().a().href("_blank", _webApp_ + "file/").iText(FA_FILES_O, "fa").text("文件资料");
        ul.li().a().href("_blank", _webApp_ + "art/").iText(FA_SITEMAP, "fa").text("产品结构");
        ul.li().a().href("_blank", _webApp_ + "sales").iText(FA_TABLE, "fa").text("进销存");
        ul.li().a().href("_blank", _webApp_ + "man").iText(FA_RANDOM, "fa").text("生产流程");
        ul.li().a().href("_blank", _webApp_ + "acc").iText(FA_USD, "fa").text("财务流程");

        cell = row.td();
        cell.b().text("工具");
        ul = cell.ul();
        // http://www.online-calculator.com/simple-full-screen-calculator/
        ul.li().a().href("_blank", "http://web2.0calc.com/").iText(FA_CALCULATOR, "fa").text("计算器");
        ul.li().a().href("_blank", "http://www.laohuangli.net/").iText(FA_CALENDAR, "fa").text("老黄历");
        ul.li().a().href("_blank", "http://www.boc.cn/sourcedb/whpj/").iText(FA_EXCHANGE, "fa").text("外汇牌价");
        ul.li().a().href("_blank", "http://www.qg68.cn/news/").iText(FA_BOOK, "fa").text("管理文库");

        cell = row.td();
        cell.b().text("服务");
        ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "service/qq").iText(FA_QQ, "fa").text("客服");
        ul.li().a().href("_blank", _webApp_ + "service/purchase").iText(FA_SHOPPING_CART, "fa").text("购买产品");
        ul.li().a().href("_blank", _webApp_ + "service/promote").iText(FA_VOLUME_UP, "fa").text("企业推介");
        ul.li().a().href("_blank", _webApp_ + "service/solution").iText(FA_CAR, "fa").text("终端建设");
        ul.li().a().href("_blank", _webApp_ + "service/pad").iText(FA_ANDROID, "fa").text("手机/平板");
        ul.li().a().href("_blank", _webApp_ + "service/qrcode").iText(FA_QRCODE, "fa").text("二维码");
        ul.li().a().href("_blank", _webApp_ + "service/rfid").iText(FA_WIFI, "fa").text("RFID");

        cell = row.td();
        cell.b().text("案例");
        ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "case/zjhf").iText(FA_STAR_O, "fa").text("三元风机");
        ul.li().a().href("_blank", _webApp_ + "case/blgd").iText(FA_STAR_O, "fa").text("宝龙光电");
        ul.li().a().href("_blank", _webApp_ + "case/hxgg").iText(FA_STAR_O, "fa").text("鸿翔钢结构");
        ul.li().a().href("_blank", _webApp_ + "case/sdty").iText(FA_STAR_O, "fa").text("时道太阳能");
        ul.li().a().href("_blank", _webApp_ + "case/hmzy").iText(FA_STAR_O, "fa").text("恒茂制衣");
        ul.li().a().href("_blank", _webApp_ + "case/dehg").iText(FA_STAR_O, "fa").text("德尔化工");
        ul.li().a().href("_blank", _webApp_ + "case/yxwdz").iText(FA_STAR_O, "fa").text("亚芯微电子");

        cell = row.td();
        cell.b().text("维护");
        ul = cell.ul();
        ul.li().a().href("_blank", _webApp_ + "util/issue").iText(FA_BUG, "fa").text("问题报告");
        ul.li().a().href("_blank", _webApp_ + "util/dashboard").iText(FA_BUILDING_O, "fa").text("机房状态");
        ul.li().a().href("_blank", _webApp_ + "util/backup").iText(FA_DATABASE, "fa").text("数据备份");
        ul.li().a().href("_blank", _webApp_ + "util/restore").iText(FA_AMBULANCE, "fa").text("灾难恢复");

        out.hr().style("margin: .5em 0");
        HtmlDiv div = out.div().align("center").class_("zu-footcopy");
        div.text("解决方案提供者 (C) 浙江省海宁市智恒软件有限公司 2010-2015 ");
        div.a().href("_blank", "about").text("(关于/联系)");
    }

}
