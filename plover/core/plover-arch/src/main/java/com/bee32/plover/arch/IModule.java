package com.bee32.plover.arch;

import com.bee32.plover.arch.credit.Credit;

public interface IModule
        extends IComponent {

    Credit getCredit();

    /**
     * Get the copyright string.
     *
     * @return <code>null</code> means copy-left.
     */
    String getCopyright();

}
