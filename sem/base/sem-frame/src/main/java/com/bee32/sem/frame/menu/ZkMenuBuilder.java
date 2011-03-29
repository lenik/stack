package com.bee32.sem.frame.menu;

import java.net.URL;

import org.zkoss.zul.Menu;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.api.Menuitem;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.sem.frame.action.IAction;

public class ZkMenuBuilder {

    /**
     * Build a Zk Menu from the top-level {@link MenuBar} model.
     */
    public static Menu buildMenu(MenuBar menuBar) {
        for (IMenuEntry topMenu : menuBar) {
            Menupopup popup = buildMenupopup(topMenu);
            // ...
        }
        return null;
    }

    /**
     * Build a Zk popup menu from a starting menu entry, which is a pre-built node in the menu tree.
     *
     * The given menu entry should be renderred as the title bar if there's any.
     *
     * The children under the given menu entry should be renderred as menu items within the
     * menupopup.
     */
    public static Menupopup buildMenupopup(IMenuEntry menuEntry) {
        return null;
    }

    /**
     * Build a Zk menu item from a specific menu entry.
     */
    public static Menuitem buildMenuitem(IMenuEntry menuEntry) {
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
