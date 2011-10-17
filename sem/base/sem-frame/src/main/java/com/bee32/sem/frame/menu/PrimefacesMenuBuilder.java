package com.bee32.sem.frame.menu;

import javax.faces.component.UIComponent;
import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.menuitem.MenuItem;
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
    public MenuModel buildMenubar(IMenuNode virtualRoot) {
        MenuModel model = new DefaultMenuModel();
        for (IMenuNode root : virtualRoot) {
            Object obj = convert(root);
            if (obj == null) {
                continue;
            } else if (obj instanceof Submenu) {
                Submenu submenu = (Submenu) obj;
                model.addSubmenu(submenu);
            } else if (obj instanceof MenuItem) {
                MenuItem item = (MenuItem) obj;
                model.addMenuItem(item);
            } else {
                throw new IllegalUsageException("Bad converted menu type: " + obj);
            }
        }
        return model;
    }

    protected UIComponent convert(IMenuNode node) {
        boolean skipped;
        if (showAll)
            skipped = node.isEmpty();
        else
            skipped = node.isBarren();
        if (skipped)
            return null;

        IAppearance appearance = node.getAppearance();

        String label = appearance.getDisplayName();
        String tooltips = appearance.getDescription();

        if (node.isEmpty()) { // menu-item
            MenuItem item = new MenuItem();

            item.setValue(label);
            item.setHelpText(tooltips);

            IAction action = node.getAction();
            if (action != null && action.isEnabled()) {

                ILocationContext target = action.getTargetLocation();
                String href = resolve(target);
                item.setUrl(href);
            }

            return item;
        }

        else { // sub-menu
            Submenu submenu = new Submenu();
            submenu.setLabel(label);
            // submenu.setIcon("");
            for (IMenuNode childNode : node) {
                UIComponent child = convert(childNode);
                if (child == null)
                    continue;
                submenu.getChildren().add(child);
            }
            return submenu;
        }
    }

}
