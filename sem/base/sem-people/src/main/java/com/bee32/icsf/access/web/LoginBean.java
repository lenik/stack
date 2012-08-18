package com.bee32.icsf.access.web;

import java.util.List;
import java.util.Random;

import javax.faces.event.ActionEvent;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.LoginManager;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityViewBean;

// @ForEntity(value = UserPassword.class)
public class LoginBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(LoginBean.class);

    private String username;
    private String password;
    private String challenge = "C" + new Random().nextInt();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public void login(ActionEvent even) {
        User user = DATA(User.class).getByName(username);
        if (user == null) {
            uiLogger.error("用户不存在");
            return;
        }

        List<UserPassword> userPasswords = DATA(UserPassword.class).list(LoginCriteria.forUser(user));
        if (userPasswords.isEmpty()) {
            uiLogger.error("登录错误:密码不存在");
            return;
        }

        String p1 = userPasswords.get(0).getPasswd();
        String _p2 = challenge + p1 + challenge;
        String p2 = DigestUtils.shaHex(_p2);

        if (!p2.equals(password)) {
            uiLogger.error("密码输入错误");
            logger.debug("Login: p1=" + p1 + ", _p2=" + _p2 + ", p2=" + p2 + ", expected=" + password);
            return;
        }

        LoginManager loginManager = LoginManager.getInstance();
        try {
            loginManager.logIn(user);
        } catch (Exception e) {
            uiLogger.error("登陆失败", e);
            return;
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("loggedIn", true);

        uiLogger.info("登录成功");
    }

}
