package com.bee32.plover.orm.util;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.test.DefaultTestSessionFactoryBean;
import com.bee32.plover.orm.context.TestDataConfig;
import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UseUnitUtil;
import com.bee32.plover.test.WiredTestCase;

@Import({ TestDataConfig.class })
public abstract class WiredDaoTestCase
        extends WiredTestCase {

    @Inject
    protected SessionFactory sessionFactory;

    private transient HibernateDaoSupport support;

    public WiredDaoTestCase() {
        PersistenceUnit unit = UseUnitUtil.getUseUnit(getClass());
        DefaultTestSessionFactoryBean.setForceUnit(unit);
    }

    protected HibernateDaoSupport getSupport() {
        if (support == null) {
            synchronized (this) {
                if (support == null) {
                    support = new HibernateDaoSupport();
                    support.setSessionFactory(sessionFactory);
                    support.afterPropertiesSet();
                }
            }
        }
        return support;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public HibernateTemplate getHibernateTemplate() {
        return getSupport().getHibernateTemplateEx();
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadNormalSamples();
    }

}
