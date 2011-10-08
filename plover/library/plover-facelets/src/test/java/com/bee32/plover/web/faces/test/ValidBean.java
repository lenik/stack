package com.bee32.plover.web.faces.test;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class ValidBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    @Size(min = 1, message = "Please enter the Email")
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", message = "Email format is invalid.")
    private String email;

    @Size(min = 5, max = 20, message = "Please enter a valid username (5-20 characters)")
    private String userName;

    @Size(min = 5, max = 20, message = "Please enter a valid password (5-10 characters)")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    // @Size(min = 5, max = 20, message = "Please enter a valid username (5-20 characters)")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}