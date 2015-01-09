package com.bee32.zebra.oa.login;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlButtonTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlFormTag;
import net.bodz.bas.html.dom.tag.HtmlHeadTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlHeadData;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.util.DefaultForm_htm;
import net.bodz.bas.http.ctx.CurrentHttpService;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.htm.RespTemplate;
import com.tinylily.model.base.security.LoginContext;

public class LoginFormVbo
        extends RespTemplate<LoginForm>
        implements IFontAwesomeCharAliases {

    public LoginFormVbo() {
        super(LoginForm.class);
    }

    @Override
    public boolean isOrigin(LoginForm value) {
        return true;
    }

    @Override
    public void preview(IHtmlViewContext ctx, IUiRef<LoginForm> ref, IOptions options) {
        super.preview(ctx, ref, options);
        IHtmlHeadData metaData = ctx.getHeadData();
        metaData.addDependency("all-effects", PSEUDO);
    }

    @Override
    public IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, IUiRef<LoginForm> ref, IOptions options)
            throws ViewBuilderException, IOException {
        if (enter(ctx))
            return null;

        HtmlHeadTag head = out.head().id("_head");
        respHead(ctx, head);
        head.link().css(_jQueryUIThemes_ + "black-tie/jquery.ui.all.css");
        head.link().css("login.css");

        out = out.body();

        HtmlDivTag dialog = out.div().id("dialog1").class_("dialog").style("width: 400px");
        dialog.title("登录");

        HtmlFormTag form = dialog.form().method("post").action("?");

        new DefaultForm_htm<LoginForm>(true).buildHtmlView(ctx, form, ref, options);

        if ("1".equals(ctx.getRequest().getParameter("logout"))) {
            CurrentHttpService.getSession().removeAttribute(LoginContext.ATTRIBUTE_KEY);
        }

        if (ctx.getRequest().getParameter("userName") != null) {
            LoginForm loginForm = ref.get();
            try {
                loginForm.populate(ctx.getRequest().getParameterMap());
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            loginForm.login(ctx, form);
        }

        form.hr();
        HtmlDivTag buttons = form.id("loginForm").div().align("right");
        HtmlButtonTag submit = buttons.button().type("submit");
        submit.span().class_("fa icon").text(FA_UNLOCK);
        submit.text("登录");

        HtmlButtonTag reset = buttons.button().type("reset");
        reset.span().class_("fa icon").text(FA_ERASER);
        reset.text("清除");

        out.script().javascriptSrc("login.js");
        return out;
    }

}
