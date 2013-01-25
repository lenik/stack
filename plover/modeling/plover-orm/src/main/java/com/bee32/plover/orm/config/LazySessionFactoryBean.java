package com.bee32.plover.orm.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.bee32.plover.thirdparty.hibernate.util.LazyInitSessionFactory;

/**
 * Choose to use AnnotationSessionFactoryBean to enable JPA annotations instead of .hbm.xml config
 * files.
 */
public abstract class LazySessionFactoryBean
        extends AnnotationSessionFactoryBean {

    static Logger logger = LoggerFactory.getLogger(LazySessionFactoryBean.class);

    class LazyConfigureSessionFactory
            extends LazyInitSessionFactory {

        private static final long serialVersionUID = 1L;

        @Override
        protected SessionFactory newSessionFactory()
                throws Exception {

            LazySessionFactoryBean sfb = LazySessionFactoryBean.this;

            logger.debug("Lazy initialize session-factory: " + this);
            logger.debug("    SFB = " + sfb);

            lazyConfigure();

            return _buildSessionFactory();
        }
    }

    protected abstract void lazyConfigure();

    @Override
    protected SessionFactory buildSessionFactory()
            throws Exception {
        return new LazyConfigureSessionFactory();
    }

    private SessionFactory _buildSessionFactory()
            throws Exception {
        return super.buildSessionFactory();
    }

}
