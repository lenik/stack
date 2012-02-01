package com.bee32.plover.faces.test;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;

import com.bee32.plover.faces.view.PerView;
import com.bee32.plover.faces.view.ViewBean;
import com.bee32.plover.model.validation.core.NLength;

@PerView
public class AutoUpdateBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    String message;

    @NotNull
    @NLength(min = 10)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return "Status: the current message is " + message;
    }

    public void update() {
        UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
        uiLogger.info("Info," + message + ", cc=" + root.getChildCount());
    }

}
