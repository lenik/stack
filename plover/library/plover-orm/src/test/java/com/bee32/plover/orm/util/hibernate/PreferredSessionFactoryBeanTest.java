package com.bee32.plover.orm.util.hibernate;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.orm.hibernate3.AbstractSessionFactoryBean;

import com.bee32.plover.test.WiredTestCase;

public class PreferredSessionFactoryBeanTest
        extends WiredTestCase {

    @Test
    public void registerTestBean() {
        AbstractSessionFactoryBean testSFB = PreferredSessionFactoryBean.candidates.get("test");
        assertNotNull(testSFB);
    }

    @Inject
    SessionFactory sessionFactory;

    @Test
    public void gotAnySessionFactory() {
        assertNotNull(sessionFactory);
    }

    static {
        PreferredSessionFactoryBean.setPreferredName("test");
    }

}
