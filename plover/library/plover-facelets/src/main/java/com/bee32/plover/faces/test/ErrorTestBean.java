package com.bee32.plover.faces.test;

import javax.faces.bean.ViewScoped;
import javax.free.IdentifiedException;

import com.bee32.plover.faces.view.ViewBean;

@ViewScoped
public class ErrorTestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public String getId() {
        int id = System.identityHashCode(this);
        return getClass().getName() + "@" + id;
    }

    public String getError() {
        throw new IdentifiedException("A sample exception.");
    }

}
