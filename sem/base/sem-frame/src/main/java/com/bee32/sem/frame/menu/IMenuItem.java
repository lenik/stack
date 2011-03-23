package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.IComponent;
import com.bee32.sem.frame.action.IAction;

public interface IMenuItem
        extends IComponent {

    /**
     * The group order will take over this order.
     *
     * @return MenuGroup literal, or <code>null</code> if this item doesn't belong to any group.
     */
    MenuSection getMenuGroup();

    /**
     * Display order of this menu item.
     *
     * @return An integer, smaller get higher priority.
     */
    int getOrder();

    /**
     * @return <code>null</code> if there's no sub menu.
     */
    IMenu getSubMenu();

    /**
     * Get the action for this menu item.
     */
    IAction getAction();

}
