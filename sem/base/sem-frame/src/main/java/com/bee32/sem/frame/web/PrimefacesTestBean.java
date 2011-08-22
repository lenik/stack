package com.bee32.sem.frame.web;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class PrimefacesTestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return "Updated: " + message;
    }

    public void update() {
        uiLogger.info("Info: " + message);
    }

}
