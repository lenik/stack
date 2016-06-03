package com.bee32.zebra.oa.login;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.db.ibatis.IMapperProvider;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.http.ctx.CurrentHttpService;
import net.bodz.bas.meta.decl.Priority;
import net.bodz.bas.repr.form.meta.TextInput;
import net.bodz.bas.site.vhost.VhostDataContexts;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.lily.model.base.security.LoginData;
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.base.security.impl.UserMapper;
import net.bodz.lily.model.base.security.impl.UserMask;

import com.bee32.zebra.tk.htm.GenericForm;

/**
 * 登录
 */
public class LoginForm
        extends GenericForm {

    private int siteId;
    private String userName;
    private String password = "";
    private String referer;

    public LoginForm() {
        HttpSession session = CurrentHttpService.getSession();
        LoginData lc = (LoginData) session.getAttribute(LoginData.ATTRIBUTE_KEY);
        if (lc != null) {
            // siteId = lc.virtualHost.getId();
            userName = lc.user.getLoginName();
            password = lc.user.getPassword();
        }
    }

    /**
     * @label 站点
     */
    // @Proposals(list = )
    @Priority(1)
    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    /**
     * @label 用户名
     * @placeholder 输入用户名…
     */
    @Priority(2)
    @TextInput(maxLength = User.N_LOGIN_NAME)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @label 密码
     * @placeholder 输入密码…
     */
    @Priority(3)
    @TextInput(maxLength = 20 /* User.N_PASSWORD */, echoChar = '*')
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(IHtmlViewContext ctx, IHtmlOut out) {
        DataContext dataContext = VhostDataContexts.getInstance().forCurrentRequest();
        IMapperProvider mapperProvider = dataContext.getMapperProvider();
        UserMapper mapper = mapperProvider.getMapper(UserMapper.class);

        UserMask mask = new UserMask();
        mask.setCodeName(userName);
        mask.password = password;

        List<User> selection = mapper.filter(mask);
        if (selection.isEmpty()) {
            out.div().class_("error").text("用户或密码错误。");
            return;
        }

        LoginData lc = new LoginData();
        lc.user = selection.get(0);

        HttpServletRequest request = CurrentHttpService.getRequestOpt();
        if (request != null) {
            String remoteAddr = request.getRemoteAddr();
            Inet4Address inet4;
            try {
                inet4 = (Inet4Address) Inet4Address.getByName(remoteAddr);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            lc.user.setLastLoginIP(inet4);
            lc.user.setLastLoginTime(System.currentTimeMillis());
        }

        ctx.getSession().setAttribute(LoginData.ATTRIBUTE_KEY, lc);
        out.div().class_("success").text("登录成功。");

        String target;
        if (referer != null && !referer.contains("login"))
            target = referer;
        else
            target = _webApp_.toString(); // + "console/";

        HtmlDiv goDiv = out.div().text("如果浏览器没有跳转至主页面，请点击[");
        goDiv.a().href(target).text("此处");
        goDiv.text("]。");
        out.script().text("location.href=\"" + target + "\";");
    }

    @Override
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        siteId = map.getInt("siteId", siteId);
        userName = map.getString("userName", userName);
        password = map.getString("password", "");
        referer = map.getString("referer");
    }

}
