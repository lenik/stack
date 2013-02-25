package com.bee32.sem.frame.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.action.IAction;

public class PrimefacesMenuBuilder
        extends AbstractMenuBuilder<MenuModel> {

    public PrimefacesMenuBuilder() {
        this(null);
    }

    public PrimefacesMenuBuilder(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected MenuModel buildMenubarImpl(IMenuNode virtualRoot) {
        MenuModel model = new DefaultMenuModel();
        Submenu topMenu = (Submenu) convert(virtualRoot);
        // Empty menubar may be reduced to null.
        if (topMenu != null) {
            List<UIComponent> contents = model.getContents();
            contents.addAll(topMenu.getChildren());
        }
        return model;
    }

    /**
     * @return <code>null</code> if the node is empty and reduced.
     */
    protected UIComponent convert(IMenuNode node) {
        int flags = node.getFlags();
        if ((flags & IMenuEntry.HIDDEN) != 0)
            return null;

        if ((flags & IMenuEntry.SEPARATOR) != 0)
            return new Separator();

        if (showAll) {
            if (node.isEmpty())
                return null;
        } else {
            if (node.isBarren())
                return null;
        }

        IAppearance appearance = node.getAppearance();

        String label = appearance.getDisplayName();
        if (label == null || label.isEmpty())
            label = node.getName();

        // String tooltips = appearance.getDescription();

        if (node.isEmpty()) { // menu-item
            MenuItem menuItem = new MenuItem();

            menuItem.setValue(label);
            // item.setHelpText(tooltips);

            // menuItem.setIcon("css-name");

            IAction action = node.getAction();
            boolean actionUsed = false;
            if (action != null && action.isEnabled()) {
                ILocationContext target = action.getTargetLocation();
                if (target != null) {
                    String href = resolve(target);
                    menuItem.setUrl(href);
                    actionUsed = true;
                }
                ActionListener listener = action.getActionListener();
                if (listener != null) {
                    menuItem.addActionListener(listener);
                    menuItem.setAjax(false);
                    actionUsed = true;
                }
            }
            menuItem.setDisabled(!actionUsed);
            return menuItem;
        }

        else { // sub-menu
            Submenu submenu = new Submenu();
            submenu.setLabel(label);
            // submenu.setIcon("");

            List<IMenuNode> children = new ArrayList<IMenuNode>(node.getChildren());
            Collections.sort(children, MenuEntryComparator.INSTANCE);

            for (IMenuNode childNode : children) {
                UIComponent child = convert(childNode);
                if (child == null)
                    continue;
                submenu.getChildren().add(child);
            }
            return submenu;
        }
    }

}
