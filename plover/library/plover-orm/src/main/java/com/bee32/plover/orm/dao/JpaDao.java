package com.bee32.plover.orm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class JpaDao
        extends JpaDaoSupport {

    @Override
    protected JpaTemplate createJpaTemplate(EntityManagerFactory entityManagerFactory) {
        return new JpaTemplate(entityManagerFactory);
    }

    @Override
    protected JpaTemplate createJpaTemplate(EntityManager entityManager) {
        return new JpaTemplate(entityManager);
    }

}
