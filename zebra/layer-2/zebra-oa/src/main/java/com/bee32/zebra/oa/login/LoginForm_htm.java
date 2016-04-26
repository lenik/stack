package com.bee32.zebra.oa.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlHeadData;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlButton;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlForm;
import net.bodz.bas.html.io.tag.HtmlHead;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.util.DefaultForm_htm;
import net.bodz.bas.http.ctx.CurrentHttpService;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.security.LoginContext;

import com.bee32.zebra.tk.htm.RespTemplate;

public class LoginForm_htm
        extends RespTemplate<LoginForm>
        implements IFontAwesomeCharAliases {

    public LoginForm_htm() {
        super(LoginForm.class);
    }

    @Override
    public boolean isOrigin(LoginForm value) {
        return true;
    }

    @Override
    public void precompile(IHtmlViewContext ctx, IUiRef<LoginForm> ref) {
        super.precompile(ctx, ref);
        IHtmlHeadData metaData = ctx.getHeadData();
        metaData.addDependency("all-effects", PSEUDO);
    }

    @Override
    public IHtmlOut buildHtmlViewStart(IHtmlViewContext ctx, IHtmlOut out, IUiRef<LoginForm> ref)
            throws ViewBuilderException, IOException {
        if (fn.redirect.addSlash(ctx))
            return null;

        HtmlHead head = out.head().id("_head");
        respHead(ctx, head);
        head.link().css(_jQueryUIThemes_ + "black-tie/jquery.ui.all.css");
        head.link().css("login.css");

        out = out.body();

        HtmlDiv dialog = out.div().id("dialog1").class_("dialog").style("width: 400px");
        dialog.title("登录");

        HtmlForm form = dialog.form().id("loginForm").method("post").action("?");

        new DefaultForm_htm<LoginForm>(true).buildHtmlViewStart(ctx, form, ref);

        HttpServletRequest request = ctx.getRequest();
        String referer = request.getHeader("Referer");
        if (referer != null)
            form.input().type("hidden").name("referer").value(referer);

        if ("1".equals(request.getParameter("logout"))) {
            CurrentHttpService.getSession().removeAttribute(LoginContext.ATTRIBUTE_KEY);
        }

        if (request.getParameter("userName") != null) {
            LoginForm loginForm = ref.get();
            try {
                loginForm.populate(request.getParameterMap());
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            loginForm.login(ctx, form);
        }

        form.hr();
        HtmlDiv buttons = form.div().align("right");
        HtmlButton submit = buttons.button().type("submit");
        submit.span().class_("fa icon").text(FA_UNLOCK);
        submit.text("登录");

        HtmlButton reset = buttons.button().type("reset");
        reset.span().class_("fa icon").text(FA_ERASER);
        reset.text("清除");

        out.script().javascriptSrc("login.js");
        return out;
    }

}
