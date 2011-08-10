package com.bee32.sem.sandbox;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.orm.util.EntityViewBean;

@Scope("view")
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
