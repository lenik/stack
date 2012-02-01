package com.bee32.plover.faces.misc;

import org.mortbay.resource.Resource;

import com.bee32.plover.faces.view.ViewBean;

public class JettyCacheSwitcherBean
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
