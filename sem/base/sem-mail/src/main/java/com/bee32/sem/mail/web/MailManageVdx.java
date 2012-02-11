package com.bee32.sem.mail.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;

import com.bee32.plover.orm.util.EntityViewBean;

public abstract class MailManageVdx
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String MAIN_TAB_VIEW = "mainForm:mainTabView";
    static final String BOX_TAB = "mainForm:mainTabView:boxTab";
    static final String CONTENT_TAB = "mainForm:mainTabView:contentTab";
    static final String WRITE_TAB = "mainForm:mainTabView:writeTab";

    protected TabView getMainTabView() {
        return (TabView) ctx.view.findComponent(MAIN_TAB_VIEW);
    }

    protected Tab getBoxTab() {
        return (Tab) ctx.view.findComponent(BOX_TAB);
    }

    protected UIComponent getContentTab() {
        return ctx.view.findComponent(CONTENT_TAB);
    }

    protected UIComponent getWriteTab() {
        return ctx.view.findComponent(WRITE_TAB);
    }

    protected int getActiveBoxIndex(String tabTitle) {
        int index = 0;
        TabView mainTabView = (TabView) ctx.view.findComponent(MAIN_TAB_VIEW);
        List<UIComponent> subTabs = mainTabView.getChildren();
        List<Tab> renderedTabs = new ArrayList<Tab>();
        for (UIComponent uicomponent : subTabs) {
            Tab tab = (Tab) uicomponent;
            if (tab.isRendered())
                renderedTabs.add(tab);
        }

        for (Tab tab : renderedTabs) {
            String title = tab.getTitle();
            if (title.equals(tabTitle))
                index = subTabs.indexOf(tab);
        }
        return index;
    }
}
