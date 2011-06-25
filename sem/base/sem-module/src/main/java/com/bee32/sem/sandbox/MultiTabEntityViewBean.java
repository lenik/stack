package com.bee32.sem.sandbox;

import com.bee32.plover.orm.util.EntityViewBean;

public class MultiTabEntityViewBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    int activeTab;

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

}
