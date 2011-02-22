package com.bee32.plover.arch;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.locator.IObjectLocator;

public interface IModule
        extends IComponent, IObjectLocator {

    /**
     * Get the module credit.
     *
     * @return Credit object, <code>null</code> if no credit info.
     */
    Credit getCredit();

    /**
     * Get the copyright string.
     *
     * @return <code>null</code> means copy-left.
     */
    String getCopyright();

}
