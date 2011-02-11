package com.bee32.plover.orm.dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDao
        extends HibernateDaoSupport {

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }

}
