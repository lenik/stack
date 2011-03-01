package com.bee32.plover.view;

import javax.servlet.ServletResponse;

import com.bee32.plover.util.Mime;

public abstract class AbstractDisplay
        implements IDisplay {

    private Mime preferredContentType;

    @Override
    public Mime getPreferredContentType() {
        return preferredContentType;
    }

    public void setPreferredContentType(Mime preferredContentType) {
        this.preferredContentType = preferredContentType;
    }

    @Override
    public Object getParentComposite() {
        return null;
    }

    @Override
    public ServletResponse getResponse() {
        // Returns the mock-ed one?
        return null;
    }

}
