package com.bee32.sem.frame.menu;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.servlet.util.ThreadServletContext;

public abstract class AbstractMenuBuilder<T>
        implements IMenuBuilder<T> {

    boolean showAll = false;
    final HttpServletRequest request;

    protected AbstractMenuBuilder(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean isShowAll() {
        return showAll;
    }

    @Override
    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    protected String resolve(ILocationContext location) {
        HttpServletRequest request = this.request;
        if (request == null)
            request = ThreadServletContext.getRequestOpt();

        if (request == null)
            return location.toString();
        else
            return location.resolve(request);
    }

}
