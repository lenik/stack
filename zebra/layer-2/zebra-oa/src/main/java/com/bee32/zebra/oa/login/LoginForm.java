package com.bee32.zebra.oa.login;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.bodz.bas.db.ibatis.IMapperProvider;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.http.ctx.CurrentHttpService;
import net.bodz.bas.meta.decl.Priority;
import net.bodz.bas.repr.form.meta.TextInput;
import net.bodz.lily.model.base.security.LoginContext;
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.base.security.impl.UserCriteria;
import net.bodz.lily.model.base.security.impl.UserMapper;
import net.bodz.lily.model.sea.QVariantMap;

import com.bee32.zebra.tk.htm.GenericForm;
import com.bee32.zebra.tk.sql.VhostDataService;

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
        LoginContext lc = (LoginContext) session.getAttribute(LoginContext.ATTRIBUTE_KEY);
        if (lc != null) {
            // siteId = lc.virtualHost.getId();
            userName = lc.user.getLoginName();
            password = lc.user.getPassword();
        }
    }

    /**
     * 站点
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
     * 用户名
     * 
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
     * 密码
     * 
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

    public void login(IHtmlViewContext ctx, IHtmlTag out) {
        IMapperProvider mapperProvider = VhostDataService.forCurrentRequest().getMapperProvider();
        UserMapper mapper = mapperProvider.getMapper(UserMapper.class);

        UserCriteria criteria = new UserCriteria();
        criteria.setCodeName(userName);
        criteria.password = password;

        List<User> selection = mapper.filter(criteria);
        if (selection.isEmpty()) {
            out.div().class_("error").text("用户或密码错误。");
            return;
        }

        LoginContext lc = new LoginContext();
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

        ctx.getSession().setAttribute(LoginContext.ATTRIBUTE_KEY, lc);
        out.div().class_("success").text("登录成功。");

        String target;
        if (referer != null && !referer.contains("login"))
            target = referer;
        else
            target = _webApp_.toString(); // + "console/";

        HtmlDivTag goDiv = out.div().text("如果浏览器没有跳转至主页面，请点击[");
        goDiv.a().href(target).text("此处");
        goDiv.text("]。");
        out.script().text("location.href=\"" + target + "\";");
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        siteId = map.getInt("siteId", siteId);
        userName = map.getString("userName", userName);
        password = map.getString("password", "");
        referer = map.getString("referer");
    }

}
