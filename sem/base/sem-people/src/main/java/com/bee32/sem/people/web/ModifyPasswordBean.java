package com.bee32.sem.people.web;

import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.LoginInfo;
import com.bee32.icsf.login.UserPassword;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.principal.User;

@Scope("view")
public class ModifyPasswordBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private String oldPass;
    private String newPass;
    private String confirmPass;
    private String challenge = "C" + new Random().nextInt();

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public void modify() {
        if (!newPass.equals(confirmPass)) {
            uiLogger.error("新密码和新密码确认不匹配");
            return;
        }

        User u = LoginInfo.getInstance().getInternalUser();
        List<UserPassword> plist = serviceFor(UserPassword.class).list(LoginCriteria.forUser(u));
        if (plist.isEmpty()) {
            uiLogger.warn("密码不存在");
            return;
        }
        String p1 = plist.get(0).getPasswd();

        if (p1.equals(oldPass)) {
            // 用户输入的旧密码正确
            plist.get(0).setPasswd(newPass);
            serviceFor(UserPassword.class).saveOrUpdate(plist.get(0));

            uiLogger.info("修改密码成功");
        } else {
            // 旧密码不正确
            uiLogger.error("旧密码输入错误");
        }
    }

}
