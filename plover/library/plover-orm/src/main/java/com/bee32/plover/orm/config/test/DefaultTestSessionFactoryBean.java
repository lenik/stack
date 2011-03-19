package com.bee32.plover.orm.config.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.qualifier.TestPurpose;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnitSelection;

@Component
@TestPurpose
@Lazy
public class DefaultTestSessionFactoryBean
        extends TestPurposeSessionFactoryBean {

    public DefaultTestSessionFactoryBean() {
        super();
    }

    /**
     * @see CustomizedSessionFactoryBean
     */
    @Override
    protected PersistenceUnitSelection getPersistenceUnitSelection() {
        Class<?> sfbClass = getClass();
        return PersistenceUnitSelection.getContextSelection(sfbClass);
    }

}
