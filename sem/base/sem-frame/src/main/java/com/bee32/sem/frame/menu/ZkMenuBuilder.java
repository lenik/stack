package com.bee32.sem.frame.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections15.Closure;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.action.IAction;

public class ZkMenuBuilder
        extends AbstractMenuBuilder<Menubar> {

    private final MenuNode virtualRoot;
    private final Menubar menubar;

    private boolean built;

    public ZkMenuBuilder(MenuNode virtualRoot, Menubar menubar, HttpServletRequest request) {
        super(request);
        if (virtualRoot == null)
            throw new NullPointerException("virtualRoot");
        if (menubar == null)
            throw new NullPointerException("menubar");
        this.virtualRoot = virtualRoot;
        this.menubar = menubar;
    }

    /**
     * Build a Zk Menu from the top-level {@link MenuModel} model.
     */
    public synchronized Menubar buildMenubar() {
        if (!built) {
            buildMenubar(virtualRoot);
            built = true;
        }
        return menubar;
    }

    @Override
    protected Menubar buildMenubarImpl(IMenuNode virtualRoot) {
        Menubar menubar = this.menubar;
        // or create new one?
        for (IMenuNode root : virtualRoot) {
            Menu rootMenu = buildMenu(root);
            menubar.appendChild(rootMenu);
        }
        return menubar;
    }

    /**
     * Build a Zk popup menu from a starting menu node, which is a pre-built node in the menu tree.
     *
     * The given menu node should be renderred as the title bar if there's any.
     *
     * The children under the given menu node should be renderred as menu items within the
     * menupopup.
     */
    public Menu buildMenu(IMenuNode menuNode) {
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
    public Menuitem buildMenuitem(IMenuNode menuNode) {
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

            ILocationContext target = action.getTargetLocation();
            if (target != null) {
                String href = target.resolveAbsolute(request);
                menuitem.setHref(href);
            }
        }

        return menuitem;
    }

}
