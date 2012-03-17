package com.bee32.plover.faces.test;

import org.primefaces.context.RequestContext;

import com.bee32.plover.faces.view.ViewBean;

public class GlobalCounterBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public synchronized void increment() {
        count++;
        RequestContext.getCurrentInstance().push("counter", count);
    }

}
