package com.bee32.plover.orm.context;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateInterceptor;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class PloverHibernateInterceptor
        extends HibernateInterceptor {

    static Logger logger = LoggerFactory.getLogger(PloverHibernateInterceptor.class);

    public PloverHibernateInterceptor() {
        logger.info("Create default hibernate interceptor");
    }

    @Inject
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

}
