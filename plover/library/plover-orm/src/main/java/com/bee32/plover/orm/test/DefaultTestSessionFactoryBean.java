package com.bee32.plover.orm.test;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.TestComponent;
import com.bee32.plover.inject.qualifier.TestPurpose;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.config.TestPurposeSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnitSelection;

@TestComponent
@TestPurpose
@Lazy
public class DefaultTestSessionFactoryBean
        extends TestPurposeSessionFactoryBean {

    public DefaultTestSessionFactoryBean() {
        super("test/default");
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
