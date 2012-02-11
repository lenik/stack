package com.bee32.icsf.access.web;

import java.util.List;
import java.util.Random;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.EntityViewBean;

@ForEntity(UserPassword.class)
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

        User u = SessionUser.getInstance().getInternalUser();
        List<UserPassword> plist = ctx.data.access(UserPassword.class).list(LoginCriteria.forUser(u));
        if (plist.isEmpty()) {
            uiLogger.warn("密码不存在");
            return;
        }
        String p1 = plist.get(0).getPasswd();

        if (p1.equals(oldPass)) {
            // 用户输入的旧密码正确
            plist.get(0).setPasswd(newPass);
            ctx.data.access(UserPassword.class).saveOrUpdate(plist.get(0));

            uiLogger.info("修改密码成功");
        } else {
            // 旧密码不正确
            uiLogger.error("旧密码输入错误");
        }
    }

}
