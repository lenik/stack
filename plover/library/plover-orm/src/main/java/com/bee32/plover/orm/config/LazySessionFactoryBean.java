package com.bee32.plover.orm.config;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.bee32.plover.thirdparty.hibernate.util.LazyInitSessionFactory;

public abstract class LazySessionFactoryBean
        extends AnnotationSessionFactoryBean {

    private final String name;

    public LazySessionFactoryBean(String name) {
        if (name == null)
            throw new NullPointerException("name");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract void lazyConfigure();

    @Override
    protected SessionFactory buildSessionFactory()
            throws Exception {
        return new LazyInitSessionFactory() {

            private static final long serialVersionUID = 1L;

            @Override
            protected SessionFactory newSessionFactory()
                    throws Exception {

                lazyConfigure();

                return buildSessionFactoryImpl();
            }
        };
    }

    private SessionFactory buildSessionFactoryImpl()
            throws Exception {
        return super.buildSessionFactory();
    }

}
