package com.bee32.plover.orm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaTemplate
        extends org.springframework.orm.jpa.JpaTemplate {

    public JpaTemplate() {
        super();
    }

    public JpaTemplate(EntityManager em) {
        super(em);
    }

    public JpaTemplate(EntityManagerFactory emf) {
        super(emf);
    }

}
