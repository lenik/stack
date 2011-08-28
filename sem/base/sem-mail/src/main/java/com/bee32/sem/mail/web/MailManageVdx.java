package com.bee32.sem.mail.web;

import javax.faces.component.UIComponent;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;

import com.bee32.plover.orm.util.EntityViewBean;

public abstract class MailManageVdx
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String MAIN_TAB_VIEW = "mainForm:mainTabView";
    static final String BOX_TAB = "mainForm:boxTab";
    static final String DETAIL_TAB = "mainForm:detailTab";

    protected TabView getMainTabView() {
        return (TabView) findComponent(MAIN_TAB_VIEW);
    }

    protected Tab getBoxTab() {
        return (Tab) findComponent(BOX_TAB);
    }

    protected UIComponent getDetailTab() {
        return findComponent(DETAIL_TAB);
    }

}
