package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.IComponent;
import com.bee32.sem.frame.action.IAction;

public interface IMenuItem
        extends IComponent {

    @Override
    String getName();

    /**
     * Make this menu entry as a separator bar.
     */
    int SEPARATOR = 1 << 0;

    /**
     * Disabled menu entry should be renderred as disabled/grayed.
     */
    int DISABLED = 1 << 1;

    /**
     * Hidden menu entry should not be renderred at all.
     */
    int HIDDEN = 1 << 2;

    /**
     * The section order will take over this order.
     *
     * @return Instance of {@link MenuSection}, or <code>null</code> if this item doesn't belong to
     *         any section.
     */
    MenuSection getSection();

    /**
     * Display order of this menu item within the same section.
     *
     * @return An integer, smaller value will be displayed first.
     */
    int getOrder();

    /**
     * Get entry flags.
     *
     * @see #SEPARATOR
     * @see #DISABLED
     * @see #HIDDEN
     */
    int getFlags();

    /**
     * Get preferred CSS class attribute value.
     *
     * @return The preferred CSS class. Returns <code>null</code> if no preferred CSS class.
     */
    String getPreferredStyleClass();

    /**
     * Get preferred CSS style attribute value.
     *
     * @return The preferred CSS style. Returns <code>null</code> if no preferred CSS style.
     */
    String getPreferredStyle();

    /**
     * Get the action for this menu item.
     *
     * @return
     */
    IAction getAction();

}
