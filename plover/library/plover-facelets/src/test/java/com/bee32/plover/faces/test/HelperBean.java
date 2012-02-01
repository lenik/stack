package com.bee32.plover.faces.test;

import com.bee32.plover.faces.view.ViewBean;

public class HelperBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    protected AutoUpdateBean getAnother() {
        return getBean(AutoUpdateBean.class);
    }

    public String getAnotherMessage() {
        return "Another<" + getAnother().getMessage() + ">";
    }

    public void setAnotherMessage(String message) {
        if (!message.isEmpty()) {
            message = "Edit<" + message + ">";
            getAnother().setMessage(message);
        }
    }

}
