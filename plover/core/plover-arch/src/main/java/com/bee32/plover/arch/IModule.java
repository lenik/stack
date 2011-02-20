package com.bee32.plover.arch;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.locator.IObjectLocator;

public interface IModule
        extends IComponent, IObjectLocator {

    Credit getCredit();

    /**
     * Get the copyright string.
     *
     * @return <code>null</code> means copy-left.
     */
    String getCopyright();

}
