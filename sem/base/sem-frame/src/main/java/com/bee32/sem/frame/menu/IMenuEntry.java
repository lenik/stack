package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.IComponent;
import com.bee32.sem.frame.action.Action;
import com.bee32.sem.frame.action.IAction;

public interface IMenuEntry
        extends IComponent {

    @Override
    String getName();

    /**
     * Make this menu node as a separator bar.
     */
    int SEPARATOR = 1 << 0;

    /**
     * Disabled menu node should be renderred as disabled/grayed.
     */
    int DISABLED = 1 << 1;

    /**
     * Hidden menu node should not be renderred at all.
     */
    int HIDDEN = 1 << 2;

    /**
     * Display order of this menu item within the same section.
     *
     * @return An integer, smaller value will be displayed first.
     */
    int getOrder();

    /**
     * Set the display order of this menu item within the same section.
     *
     * @param order
     *            An integer, smaller value will be displayed first.
     */
    void setOrder(int order);

    /**
     * Get node flags.
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
     * Set the preferred CSS class attribute value.
     *
     * @param preferredStyleClass
     *            The preferred CSS class. Set to <code>null</code> if no preferred CSS class.
     */
    void setPreferredStyleClass(String preferredStyleClass);

    /**
     * Get preferred CSS style attribute value.
     *
     * @return The preferred CSS style. Returns <code>null</code> if no preferred CSS style.
     */
    String getPreferredStyle();

    /**
     * Set the preferred CSS style attribute value.
     *
     * @param preferredStyle
     *            The preferred CSS style. Set to <code>null</code> if no preferred CSS style.
     */
    void setPreferredStyle(String preferredStyle);

    /**
     * Get the action for this menu item.
     *
     * @return {@link Action}, <code>null</code> if none.
     */
    IAction getAction();

    /**
     * Set the action for this menu item.
     *
     * @param action
     *            The action, <code>null</code> if none.
     */
    void setAction(IAction action);

}
