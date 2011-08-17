package com.bee32.plover.web.faces.test;

import javax.faces.bean.ViewScoped;

import com.bee32.plover.web.faces.view.ViewBean;

@ViewScoped
public class ErrorTestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public String getId() {
        int id = System.identityHashCode(this);
        return getClass().getName() + "@" + id;
    }

    public String getError() {
        throw new RuntimeException("A sample exception.");
    }

}
