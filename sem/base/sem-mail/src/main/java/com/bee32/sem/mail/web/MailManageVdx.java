package com.bee32.sem.mail.web;

import java.util.List;

import javax.faces.component.UIComponent;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;

import com.bee32.plover.orm.util.EntityViewBean;

public abstract class MailManageVdx
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String MAIN_TAB_VIEW = "mainForm:mainTabView";
    static final String BOX_TAB = "mainForm:boxTab";
    static final String CONTENT_TAB = "mainForm:contentTab";
    static final String WRITE_TAB = "mainForm:writeTab";

    protected TabView getMainTabView() {
        return (TabView) findComponent(MAIN_TAB_VIEW);
    }

    protected Tab getBoxTab() {
        return (Tab) findComponent(BOX_TAB);
    }

    protected UIComponent getContentTab() {
        return findComponent(CONTENT_TAB);
    }

    protected UIComponent getWriteTab() {
        return findComponent(WRITE_TAB);
    }

    protected int getWriteboxIndex(String tabTitle) {
        int index = 0;
        TabView mainTabView = (TabView) findComponent(MAIN_TAB_VIEW);
        List<UIComponent> subTabs = mainTabView.getChildren();
        for (UIComponent component : subTabs) {
            Tab tab = (Tab) component;
            String title = tab.getTitle();
            if (title.equals(tabTitle))
                index = subTabs.indexOf(component);
        }
        return index;
    }
}
