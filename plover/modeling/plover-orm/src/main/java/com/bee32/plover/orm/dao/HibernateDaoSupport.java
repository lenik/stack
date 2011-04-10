package com.bee32.plover.orm.dao;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

/**
 *
 * {@inheritDoc}
 * <p>
 * New features:
 * <ul>
 * <li>Using Plover- Hibernate templates, which extends the spring one.
 * </ul>
 *
 * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
 */
public abstract class HibernateDaoSupport
        extends org.springframework.orm.hibernate3.support.HibernateDaoSupport {

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }

    public HibernateTemplate getHibernateTemplateEx() {
        return (HibernateTemplate) getHibernateTemplate();
    }

    @Inject
    void initSessionFactory(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

}
