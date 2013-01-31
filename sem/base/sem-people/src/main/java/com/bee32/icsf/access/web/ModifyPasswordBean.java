package com.bee32.icsf.access.web;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.bee32.icsf.login.LoginCriteria;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.User;
import com.bee32.plover.model.validation.core.Alphabet;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.EntityViewBean;

@ForEntity(UserPassword.class)
public class ModifyPasswordBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private String oldPassword;
    private String newPassword;
    private String passwordConfirm;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotEmpty
    @Size(min = 1, max = 12)
    @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotEmpty
    @Size(min = 1, max = 12)
    @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void modify() {
        if (!newPassword.equals(passwordConfirm)) {
            uiLogger.error("新密码和新密码确认不匹配");
            return;
        }

        User u = SessionUser.getInstance().getInternalUser();
        List<UserPassword> plist = DATA(UserPassword.class).list(LoginCriteria.forUser(u));
        if (plist.isEmpty()) {
            uiLogger.warn("密码不存在");
            return;
        }
        UserPassword userPassword = plist.get(0);
        String oldP1 = userPassword.getPasswd();

        if (oldP1.equals(oldPassword)) {
            // 用户输入的旧密码正确
            userPassword.setPasswd(newPassword);

            try {
                DATA(UserPassword.class).saveOrUpdate(userPassword);
            } catch (Exception e) {
                uiLogger.error(e, "保存密码失败");
                return;
            }

            uiLogger.info("修改密码成功");
        } else {
            // 旧密码不正确
            uiLogger.error("旧密码输入错误");
        }
    }

}
