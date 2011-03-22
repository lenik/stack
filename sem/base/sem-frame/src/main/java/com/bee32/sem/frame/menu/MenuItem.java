package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.Component;
import com.bee32.sem.frame.action.IAction;

public class MenuItem
        extends Component
        implements IMenuItem {

    private MenuGroup menuGroup;
    private int order;
    private IAction action;
    private IMenu subMenu;

    private MenuItem(String name) {
        this(name, null);
    }

    private MenuItem(String name, IAction action) {
        super(name);
        this.action = action;
    }

    @Override
    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroup menuGroup) {
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
