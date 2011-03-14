package com.bee32.plover.orm.context;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.stereotype.Component;

@Primary
@Component
@Named("transactionManager")
@Lazy
public class PloverHibernateTransactionManager
        extends HibernateTransactionManager {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PloverHibernateTransactionManager.class);

    public PloverHibernateTransactionManager() {
        logger.info("Create plover hibernate transaction manager");
    }

    @Inject
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

}
