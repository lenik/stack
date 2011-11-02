package com.bee32.plover.orm.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.orm.dao.CommonDataManager;

@ServiceTemplate
public abstract class AbstractDc
        implements IDatabaseConfigurer {

    static Logger logger = LoggerFactory.getLogger(AbstractDc.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureHibernateProperties(Properties properties)
            throws Exception {
    }

    @Override
    public void configureSessionFactoryBean(LocalSessionFactoryBean bean)
            throws Exception {
    }

    @Override
    public void commonDataInit(ApplicationContext appctx, CommonDataManager dataManager)
            throws Exception {
    }

    @Override
    public void commonDataUninit(ApplicationContext appctx, CommonDataManager dataManager)
            throws Exception {
    }

}
