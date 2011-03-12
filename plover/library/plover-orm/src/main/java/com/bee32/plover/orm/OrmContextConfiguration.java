package com.bee32.plover.orm;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateInterceptor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

@Configuration
@Lazy
public class OrmContextConfiguration {

    static Logger logger = LoggerFactory.getLogger(OrmContextConfiguration.class);

    @Inject
    SessionFactory sessionFactory;

    @Bean
    public HibernateInterceptor hibernateInterceptor() {
        logger.info("Create default hibernate interceptor");

        HibernateInterceptor hibernateInterceptor = new HibernateInterceptor();
        hibernateInterceptor.setSessionFactory(sessionFactory);

        return hibernateInterceptor;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager() {
        logger.info("Create default hibernate transaction manager");

        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);

        return hibernateTransactionManager;
    }

}
