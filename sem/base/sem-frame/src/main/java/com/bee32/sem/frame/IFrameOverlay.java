package com.bee32.sem.frame;

import com.bee32.sem.frame.menu.IMenuEntry;

public interface IFrameOverlay {

    /**
     * Get a toplevel menu with specific name.
     *
     * @return <code>null</code> if a top-level menu of with specified menu-id doesn't exist.
     */
    IMenuEntry getMenu(String menuId);

}
