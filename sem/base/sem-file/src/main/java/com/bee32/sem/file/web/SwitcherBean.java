package com.bee32.sem.file.web;

import org.mortbay.resource.Resource;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class SwitcherBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public boolean getCached() {
        return Resource.getDefaultUseCaches();
    }

    public void setCached(boolean cached) {
        System.err.println("CACHE="+cached);
        Resource.setDefaultUseCaches(cached);
    }

}
