package com.bee32.plover.orm.unit;

import java.util.Collection;

import com.bee32.plover.arch.IComponent;

public interface IPersistenceUnit
        extends IComponent {

    /**
     * The persistence unit name.
     *
     * @return Non-<code>null</code> persistence unit name.
     */
    @Override
    String getName();

    /**
     * @see org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean
     */
    Collection<Class<?>> getClasses();

}
