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
import com.bee32.plover.servlet.util.ThreadServletContext;
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
    public MenuModel buildMenubar(IMenuNode virtualRoot) {
        MenuModel model = new DefaultMenuModel();
        Submenu topMenu = (Submenu) convert(virtualRoot);
        model.getContents().addAll(topMenu.getChildren());
        return model;
    }

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
            MenuItem item = new MenuItem();

            item.setValue(label);
            // item.setHelpText(tooltips);

            IAction action = node.getAction();
            boolean actionUsed = false;
            if (action != null && action.isEnabled()) {
                ILocationContext target = action.getTargetLocation();
                if (target != null) {
                    String href = resolve(target);
                    item.setUrl(href);
                    actionUsed = true;
                }
                ActionListener listener = action.getActionListener();
                if (listener != null) {
                    item.addActionListener(listener);
                    item.setAjax(false);
                    actionUsed = true;
                }
            }
            item.setDisabled(!actionUsed);
            return item;
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

    public static PrimefacesMenuBuilder INSTANCE = new PrimefacesMenuBuilder();

}
