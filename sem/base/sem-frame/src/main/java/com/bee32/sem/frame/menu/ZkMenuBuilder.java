package com.bee32.sem.frame.menu;

import java.net.URL;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuitem;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.sem.frame.action.IAction;

public class ZkMenuBuilder {

    /**
     * Build a Zk Menu from the top-level {@link MenuBar} model.
     */
    public static void buildMenubar(Menubar menubar, MenuBar menuBar) {
        for (IMenuEntry topMenu : menuBar) {
            Menu menu = buildMenu(topMenu);
            menubar.appendChild(menu);
        }
    }

    /**
     * Build a Zk popup menu from a starting menu entry, which is a pre-built node in the menu tree.
     *
     * The given menu entry should be renderred as the title bar if there's any.
     *
     * The children under the given menu entry should be renderred as menu items within the
     * menupopup.
     */
    public static Menu buildMenu(IMenuEntry menuEntry) {
        Menu menu = new Menu();
        menu.setLabel(menuEntry.getAppearance().getDisplayName());
        Menupopup popup = new Menupopup();

        for (IMenuEntry childEntry : menuEntry) {
            if(isLeafNode(childEntry)) {
                Menuitem menuitem = buildMenuitem(childEntry);
                popup.appendChild(menuitem);
            } else {
                Menu subMenu = buildMenu(childEntry);
                popup.appendChild(subMenu);
            }
        }
        menu.appendChild(popup);
        return menu;
    }

    protected static boolean isLeafNode(IMenuEntry entry) {
        if (entry.size() == 0)
            return true;

        if (entry.size() > 1)
            return false;

        return false; //isLeafNode(entry.iterator().next());
    }

    /**
     * Build a Zk menu item from a specific menu entry.
     */
    public static Menuitem buildMenuitem(IMenuEntry menuEntry) {
        Menuitem menuitem = new Menuitem();

        IAppearance appearance = menuEntry.getAppearance();
        String id = menuEntry.getName();
        String label = appearance.getDisplayName();
        String description = appearance.getDescription();
//        URL icon = appearance.getImageMap().getImage();

        menuitem.setLabel(label);

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

                menuitem.addEventListener("onClick", new EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        //  alert();
                    }
                });


            } else {
                onClick = null;
                href = target.getLocation();
                menuitem.setHref(href);
            }
        }



        return menuitem;
    }

}
