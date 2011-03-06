package com.bee32.plover.orm.dao;

import org.hibernate.SessionFactory;

public class HibernateDaoSupport
        extends org.springframework.orm.hibernate3.support.HibernateDaoSupport {

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }

    public HibernateTemplate getHibernateTemplateEx() {
        return (HibernateTemplate) getHibernateTemplate();
    }

}
