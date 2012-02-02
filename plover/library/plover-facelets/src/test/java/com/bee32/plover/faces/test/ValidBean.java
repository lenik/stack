package com.bee32.plover.faces.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bee32.plover.faces.view.PerView;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;

@PerView
public class ValidBean
        extends ValidBase {

    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

    @NLength(min = 1, message = "Please enter the Email")
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", message = "Email format is invalid.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = TextUtil.normalizeSpace(email);
    }

    @NotNull
    // @NLength(min = 2, max = 20) //, message = "用户名长度不符 (2-20 字符)")
    @Size(min = 2, max = 20, message = "用户名长度不符 (2-20 字符)")
    public String getUserName() {
        return super.getUserName();
    }

    @Override
    public void setUserName(String userName) {
        userName = TextUtil.normalizeSpace(userName, true);
        super.setUserName(userName);
    }

    @NLength(min = 5, max = 20, message = "Please enter a valid password (5-10 characters)")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = TextUtil.normalizeSpace(password);
    }

}