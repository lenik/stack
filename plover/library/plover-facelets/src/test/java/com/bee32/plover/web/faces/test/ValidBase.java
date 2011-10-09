package com.bee32.plover.web.faces.test;

import com.bee32.plover.model.validation.Size;
import com.bee32.plover.web.faces.view.ViewBean;

public class ValidBase
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    private String userName;

    @Size(min = 5, max = 20, message = "Please enter a valid username (5-20 characters)")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
