package com.bee32.plover.orm.unit;

import java.util.Collection;

public interface IPersistenceUnitContribution {

    /**
     * The name of persistence unit to contribute to.
     *
     * @return Non-<code>null</code> persistence unit name.
     */
    String getName();

    /**
     * Get persistent classes.
     *
     * @return Non-<code>null</code> collection of persistent classes.
     */
    Collection<Class<?>> getClasses();

}
