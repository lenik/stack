package com.bee32.plover.arch.service;

import com.bee32.plover.arch.IComponent;

public interface IServiceContribution<C extends IServiceContribution<C>>
        extends IComponent {

    /**
     * Get the extension point name.
     */
    @Override
    String getName();

    /**
     * Get the contribution class.
     */
    Class<C> getContributionClass();

}
