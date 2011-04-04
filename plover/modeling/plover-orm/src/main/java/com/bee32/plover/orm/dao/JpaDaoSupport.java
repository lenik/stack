package com.bee32.plover.orm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaDaoSupport
        extends org.springframework.orm.jpa.support.JpaDaoSupport {

    @Override
    protected JpaTemplate createJpaTemplate(EntityManagerFactory entityManagerFactory) {
        return new JpaTemplate(entityManagerFactory);
    }

    @Override
    protected JpaTemplate createJpaTemplate(EntityManager entityManager) {
        return new JpaTemplate(entityManager);
    }

    public JpaTemplate getJpaTemplateEx() {
        return (JpaTemplate) getJpaTemplate();
    }

}
