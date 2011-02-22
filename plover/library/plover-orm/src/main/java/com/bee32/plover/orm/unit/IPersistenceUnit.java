package com.bee32.plover.orm.unit;

import com.bee32.plover.arch.IComponent;

public interface IPersistenceUnit
        extends IComponent {

    /**
     * The persistence unit name.
     *
     * @return Non-<code>null</code> persistence unit name.
     */
    String getName();

    Class<? extends IPersistenceUnitContribution> getContributionClass();

    Object generateHibernateMappings();

}
