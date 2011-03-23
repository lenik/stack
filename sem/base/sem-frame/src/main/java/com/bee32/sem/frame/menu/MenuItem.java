package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.Component;
import com.bee32.sem.frame.action.IAction;

public class MenuItem
        extends Component
        implements IMenuItem {

    private MenuSection menuGroup;
    private int order;
    private IAction action;
    private IMenu subMenu;

    public MenuItem(String name) {
        this(name, null);
    }

    public MenuItem(String name, IAction action) {
        super(name);
        this.action = action;
    }

    @Override
    public MenuSection getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuSection menuGroup) {
        this.menuGroup = menuGroup;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public IAction getAction() {
        return action;
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    @Override
    public IMenu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(IMenu subMenu) {
        this.subMenu = subMenu;
    }

}
