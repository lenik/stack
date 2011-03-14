package com.bee32.plover.orm.dao;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class HibernateTemplate
        extends org.springframework.orm.hibernate3.HibernateTemplate {

    public HibernateTemplate() {
        super();
    }

    public HibernateTemplate(SessionFactory sessionFactory, boolean allowCreate) {
        super(sessionFactory, allowCreate);
    }

    public HibernateTemplate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
