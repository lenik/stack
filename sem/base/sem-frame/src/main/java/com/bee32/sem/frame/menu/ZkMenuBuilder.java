package com.bee32.sem.frame.menu;

import java.net.URL;

import org.zkoss.zul.Menu;
import org.zkoss.zul.Menupopup;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.sem.frame.action.IAction;

public class ZkMenuBuilder {

    public static Menu buildMenu(MenuBar menuBar) {
        for (IMenuEntry topMenu : menuBar) {
            Menupopup popup = buildPopup(topMenu);
            // ...
        }
        return null;
    }

    public static Menupopup buildPopup(IMenuEntry menuEntry) {
        IAppearance appearance = menuEntry.getAppearance();
        String id = menuEntry.getName();
        String label = appearance.getDisplayName();
        String description = appearance.getDescription();
        URL icon = appearance.getImageMap().getImage();

        int order = menuEntry.getOrder();
        MenuSection section = menuEntry.getSection();
        if (section != null)
            order += section.getOrder() * 1000;

        String cssStyle = menuEntry.getPreferredStyle();
        String cssClass = menuEntry.getPreferredStyleClass();

        IAction action = menuEntry.getAction();
        if (action != null) {
            boolean isEnabled = action.isEnabled();

            String onClick;
            String href;

            ContextLocation target = action.getTarget();
            if (target.getContext() == LocationContextConstants.JAVASCRIPT) {
                onClick = target.getLocation();
                href = null;
            } else {
                onClick = null;
                href = target.getLocation();
            }
        }

        for (IMenuEntry childEntry : menuEntry) {
            // ...
        }

        return null;
    }

}
