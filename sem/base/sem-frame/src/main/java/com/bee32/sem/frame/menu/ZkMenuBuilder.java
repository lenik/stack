package com.bee32.sem.frame.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections15.Closure;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.sem.frame.action.IAction;

public class ZkMenuBuilder {

    /**
     * Build a Zk Menu from the top-level {@link MenuBar} model.
     */
    public static void buildMenubar(Menubar menubar, MenuBar menuBar) {
        for (IMenuNode topMenu : menuBar) {
            Menu menu = buildMenu(topMenu);
            menubar.appendChild(menu);
        }
    }

    /**
     * Build a Zk popup menu from a starting menu node, which is a pre-built node in the menu tree.
     *
     * The given menu node should be renderred as the title bar if there's any.
     *
     * The children under the given menu node should be renderred as menu items within the
     * menupopup.
     */
    public static Menu buildMenu(IMenuNode menuNode) {
        Menu menu = new Menu();
        menu.setLabel(menuNode.getAppearance().getDisplayName());
        Menupopup popup = new Menupopup();

        List<IMenuNode> children = new ArrayList<IMenuNode>(menuNode.getChildren());
        Collections.sort(children, MenuEntryComparator.INSTANCE);

        for (IMenuNode childNode : children) {
            if (isLeafNode(childNode)) {
                Menuitem menuitem = buildMenuitem(childNode);
                popup.appendChild(menuitem);
            } else {
                Menu subMenu = buildMenu(childNode);
                popup.appendChild(subMenu);
            }
        }
        menu.appendChild(popup);
        return menu;
    }

    protected static boolean isLeafNode(IMenuNode node) {
        if (node.size() == 0)
            return true;

        if (node.size() > 1)
            return false;

        return false; // isLeafNode(node.iterator().next());
    }

    /**
     * Build a Zk menu item from a specific menu node.
     */
    public static Menuitem buildMenuitem(IMenuNode menuNode) {
        final Menuitem menuitem = new Menuitem();

        IAppearance appearance = menuNode.getAppearance();
        // String id = menuNode.getName();
        String label = appearance.getDisplayName();
        // String description = appearance.getDescription();

        // URL icon = appearance.getImageMap().getImage();

        menuitem.setLabel(label);

        // MenuSection section = menuNode.getSection();

        // String cssStyle = menuNode.getPreferredStyle();
        // String cssClass = menuNode.getPreferredStyleClass();

        IAction action = menuNode.getAction();
        if (action != null) {
            boolean isEnabled = action.isEnabled();

            if (!isEnabled)
                menuitem.setAttribute("enabled", "false");

            final Closure<Event> callback = action.getZkCallback();
            if (isEnabled && callback != null) {
                menuitem.addEventListener("onClick", new EventListener() {
                    @Override
                    public void onEvent(Event event)
                            throws Exception {
                        callback.execute(event);
                    }
                });
            }

            ContextLocation target = action.getTargetLocation();
            if (target != null) {
                String href = target.getLocation();
                menuitem.setHref(href);
            }
        }

        return menuitem;
    }

}
