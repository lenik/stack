package com.bee32.icsf.access.fea1;

import java.util.Properties;

import com.bee32.plover.orm.config.test.TestPurposeSessionFactoryBean;

public class FeatureSFB
        extends TestPurposeSessionFactoryBean {

    @Override
    protected void populateHibernateProperties(Properties properties) {
        super.populateHibernateProperties(properties);

        properties.put(hbm2ddlAuto, "create-drop");
    }

}
